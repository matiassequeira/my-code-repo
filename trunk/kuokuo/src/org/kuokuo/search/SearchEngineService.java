/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.search;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.kuokuo.Configuration;
import org.kuokuo.client.data.IndexStatus;
import org.kuokuo.client.data.PagingUpdateItems;
import org.kuokuo.client.data.QueryResult;
import org.kuokuo.client.data.QueryResultItem;
import org.kuokuo.resource.ResourceDef;
import org.kuokuo.resource.ResourceReader;
import org.kuokuo.server.dao.DoubanResourceDao;
import org.kuokuo.server.dao.KuokuoItemDao;

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

    protected IndexMonitor monitor;

    private SearchEngineService()
    {
        try
        {            
            logger.info("Starting search engine service");
            
            status = new IndexStatus();
            status.setStartDate(new SimpleDateFormat("MMM d").format(new Date()));

            monitor = new IndexMonitor();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public synchronized void start()
    {
        monitor.start();
    }
    
    public synchronized void reindex() throws Exception
    {
        status.setDocCount(0);

        long start = System.currentTimeMillis();
        KuokuoItemDao dao = new KuokuoItemDao();
        dao.deleteAll();
        List<ResourceDef> resourceDefs = Configuration.getInstance().getResourceDefs();
        for (ResourceDef def : resourceDefs)
        {
            ResourceReader reader = new ResourceReader(def);
            Iterator<File> ite = reader.iterator();
            while (ite.hasNext())
            {
                dao.insertItem(ite.next(), def);
                
                status.setDocCount(status.getDocCount() + 1);
            }
        }
        dao.createFullTextSession();

        DoubanResourceDao doubanResourceDao = new DoubanResourceDao();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE , -10);
        doubanResourceDao.removeObsoleteResource(calendar.getTime());
        
        status.setIndexCost(System.currentTimeMillis() - start);
        status.setLastUpdate(new SimpleDateFormat("MMM d, H:mm").format(new Date()));
    }

    private IndexStatus status;

    public IndexStatus getIndexStatus()
    {
        return status;
    }
    
    //TODO: query to merge this with query(queryStr) method. We may get the latest media via querying lucene index
    public PagingUpdateItems getUpdateItems(int from, int len) throws Exception
    {
        List<QueryResultItem> list = new ArrayList<QueryResultItem>();
        
        PagingUpdateItems rv = new PagingUpdateItems();
        rv.setItems(list);
        rv.setStart(from);
        return rv;
    }

    public QueryResult query(String queryStr) throws Exception
    {
        KuokuoItemDao dao = new KuokuoItemDao();
        QueryResult result = new QueryResult();
        long start = System.currentTimeMillis();
        result.setTime((float) (System.currentTimeMillis() - start) / (float) 1000);
        result.setItems(dao.fullTextSearch(queryStr));
        return result;
    }
}
