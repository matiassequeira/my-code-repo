/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.search;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import org.kuokuo.client.data.IndexStatus;
import org.kuokuo.client.data.QueryResult;
import org.kuokuo.client.data.QueryResultItem;
import org.kuokuo.resource.ResourceDef;
import org.kuokuo.resource.ResourceReader;
import org.kuokuo.server.CachedDoubanService;
import org.kuokuo.server.DoubanResource;
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

    public List<QueryResultItem> getUpdateItems()
    {
        List<QueryResultItem> list = new ArrayList<QueryResultItem>();

        for (int i = updates.length - 1; i >= 0; i--)
        {
            if (updates[i] != null)
            {
                list.add(updates[i]);
            }
        }

        return list;
    }

    private SearchEngineService()
    {
        try
        {
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
        updates = new QueryResultItem[15];

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

        QueryResultItem item = new QueryResultItem();
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
            QueryResultItem item = new QueryResultItem();
            item.setScore(rs.scoreDocs[i].score);
            item.setName(doc.getField("name").stringValue());
            item.setHighlightName(highlighter.getBestFragment(analyzer, "name", doc.getField("name").stringValue()));
            item.setLastModified(doc.getField("lastModified").stringValue());
            item.setTimestamp(Long.parseLong(doc.getField("timestamp").stringValue()));
            item.setPath(doc.getField("path").stringValue());
            item.setFolder(Boolean.getBoolean(doc.getField("isFolder").stringValue()));
            list.add(item);
            
            //add data from douban
            DoubanResource dbResource = douban.getInfo(item.getName(),DoubanResourceType.MOVIE);
            if(dbResource!=null){
                item.doubanID=dbResource.id;
                item.imageURL=dbResource.imageURL;
            }
        }

        result.setItems(list);
        status.setQueryCount(status.getQueryCount() + 1);
        return result;
    }
}
