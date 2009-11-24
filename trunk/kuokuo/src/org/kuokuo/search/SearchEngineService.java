/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.search;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.kuokuo.Configuration;
import org.kuokuo.client.Search;
import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.IndexStatus;
import org.kuokuo.client.data.MovieResultItem;
import org.kuokuo.client.data.PagingUpdateItems;
import org.kuokuo.client.data.QueryResult;
import org.kuokuo.client.data.QueryResultItem;
import org.kuokuo.resource.ResourceDef;
import org.kuokuo.resource.ResourceReader;
import org.kuokuo.server.CachedDoubanService;
import org.kuokuo.server.DoubanResourceType;

import com.chenlb.mmseg4j.analysis.MaxWordAnalyzer;

/**
 * search service
 * 
 * @version Nov 15, 2009 11:26:04 AM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class SearchEngineService
{
    private static Logger logger=Logger.getLogger(SearchEngineService.class.getName());
    
    private static SearchEngineService instance;

    private static final String initLock = "";

    public static SearchEngineService getInstance()
    {
        if (instance == null)
        {
            synchronized (initLock)
            {
                instance = new SearchEngineService();
            }
        }
        return instance;
    }

    protected Directory directory;

    protected IndexWriter writer;

    protected Analyzer analyzer;

    protected IndexSearcher searcher;

    protected QueryResultItem[] updates;

    protected IndexMonitor monitor = new IndexMonitor();

    private SearchEngineService()
    {
        //this is almost the 1st server side class initied
        try
        {
            //config java.util.logging
            //logger.info("loading java.util.logging configuration");
            //LogManager.getLogManager().readConfiguration(this.getClass().getResourceAsStream("/logging.properties"));
            //logger= Logger.getLogger(SearchEngineService.class.getName());
            //logger.fine("java.util.logging configuration loaded");
            
            logger.info("Starting search engine service");
            
            status = new IndexStatus();
            status.setStartDate(new SimpleDateFormat("MMM d").format(new Date()));

            directory = new RAMDirectory();
            analyzer = new MaxWordAnalyzer();

            reindex();

            searcher = new IndexSearcher(directory, true);
            monitor.start();
            
            douban=new CachedDoubanService();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public synchronized void reindex() throws Exception
    {
        status.setDocCount(0);
        writer = new IndexWriter(directory, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
        updates = new QueryResultItem[100];

        long start = System.currentTimeMillis();
        List<ResourceDef> resourceDefs = Configuration.getInstance().getResourceDefs();
        for (ResourceDef def : resourceDefs)
        {
            ResourceReader reader = new ResourceReader(def);
            Iterator<Document> ite = reader.iterator();
            while (ite.hasNext())
            {
                Document doc = ite.next();
                writer.addDocument(doc);

                checkUpdate(doc);

                status.setDocCount(status.getDocCount() + 1);
            }
        }
        writer.commit();
        writer.optimize();
        writer.close();
        status.setIndexCost(System.currentTimeMillis() - start);
        status.setLastUpdate(new SimpleDateFormat("MMM d, H:mm").format(new Date()));
    }

    /**
     * @param doc
     */
    private void checkUpdate(Document doc)
    {
        int pos = -1;
        long target = Long.parseLong(doc.getField("timestamp").stringValue());
        for (int i = 0; i < updates.length; i++)
        {
            long timestap = 0;
            if (updates[i] != null)
            {
                timestap = updates[i].getTimestamp();
            }
            if (target < timestap)
            {
                break;
            }
            pos = i;
        }

        if (pos == -1)
            return;

        for (int i = 0; i < pos; i++)
        {
            updates[i] = updates[i + 1];
        }

        QueryResultItem item;
        if(Search.TYPE_MOVIE.equals(doc.getField("type").stringValue()))
        {
            item = new MovieResultItem();
        }
        else
        {
            item = new QueryResultItem();
        }
        item.setName(doc.getField("name").stringValue());
        item.setLastModified(doc.getField("lastModified").stringValue());
        item.setTimestamp(Long.parseLong(doc.getField("timestamp").stringValue()));
        item.setPath(doc.getField("path").stringValue());
        item.setFolder(Boolean.getBoolean(doc.getField("isFolder").stringValue()));

        updates[pos] = item;
    }

    private IndexStatus status;

    private CachedDoubanService douban;

    public IndexStatus getIndexStatus()
    {
        return status;
    }
    
    //TODO: query to merge this with query(queryStr) method. We may get the latest media via querying lucene index
    public PagingUpdateItems getUpdateItems(int from, int len) throws Exception
    {
        List<QueryResultItem> list = new ArrayList<QueryResultItem>();

        if (from < 0)
            from = 0;
        if (from > updates.length)
            from = updates.length - 1;
        
        //the update items are reverse order in array
        int start = updates.length - 1 - from;
        int end = start - len + 1;
        if (end < 0)
            end = 0;
        for (int i = start; i >= end; i--)
        {
            if (updates[i] != null)
            {
                list.add(updates[i]);
                if(updates[i].getHighlightName()==null){
                    updates[i].setHighlightName(updates[i].getName());
                }
//                if(updates[i].doubanURL==null){
//                    loadDataFromDouban(updates[i]);
//                }
            }
        }
        
        PagingUpdateItems rv = new PagingUpdateItems();
        rv.setCount(updates.length);
        rv.setItems(list);
        rv.setStart(from);
        return rv;
    }

    public QueryResult query(String queryStr) throws Exception
    {
        QueryResult result = new QueryResult();
        List<QueryResultItem> list = new ArrayList<QueryResultItem>();

        QueryParser parser = new QueryParser(Version.LUCENE_24, "name", analyzer);
        Query query = parser.parse(queryStr);
        QueryScorer scorer = new QueryScorer(query);
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<b><font color=red>", "</font></b>");
        Highlighter highlighter = new Highlighter(formatter, scorer);

        long start = System.currentTimeMillis();
        TopDocs rs = searcher.search(query, null, 20);
        result.setTime((float) (System.currentTimeMillis() - start) / (float) 1000);

        int count = rs.scoreDocs.length;
        for (int i = 0; i < count; i++)
        {
            Document doc = searcher.doc(rs.scoreDocs[i].doc);
            QueryResultItem item;
            if(Search.TYPE_MOVIE.equals(doc.getField("type")))
            {
                item = new MovieResultItem();
            }
            else
            {
                item = new QueryResultItem();
            }
            item.setScore(rs.scoreDocs[i].score);
            item.setName(doc.getField("name").stringValue());
            item.setHighlightName(highlighter.getBestFragment(analyzer, "name", doc.getField("name").stringValue()));
            item.setLastModified(doc.getField("lastModified").stringValue());
            item.setTimestamp(Long.parseLong(doc.getField("timestamp").stringValue()));
            item.setPath(doc.getField("path").stringValue());
            item.setFolder(Boolean.getBoolean(doc.getField("isFolder").stringValue()));
            list.add(item);
            
            //add data from douban
            //loadDataFromDouban(item);
        }

        result.setItems(list);
        status.setQueryCount(status.getQueryCount() + 1);
        return result;
    }
    
    public DoubanResource loadDataFromDouban(String name) throws Exception{
        DoubanResource dbResource = douban.getInfo(name,DoubanResourceType.MOVIE);
        return dbResource;
    }
}
