/**
 * 
 */
package org.kuokuo;

import org.kuokuo.client.data.AppStatus;
import org.kuokuo.search.SearchEngineService;
import org.kuokuo.server.dao.QueryInfoDao;
import org.kuokuo.server.dao.VisitorInfoDao;

/**
 * @version Jan 10, 2010 11:52:56 AM
 * @author Dingmeng (xuedm79@gmail.com)
 *
 */
public class CachedAppStatus
{
    private CachedAppStatus()
    {

    }
    
    private static long timestamp = 0;

    private transient long queryCount = 0;
    
    private transient long visitorCount = 0;
    
    public synchronized long increaseQuery()
    {
        queryCount++;
        return queryCount;
    }
    
    public synchronized long increaseVisitor()
    {
        visitorCount++;
        return visitorCount;
    }
    
    private static CachedAppStatus cached;
    
    public static synchronized CachedAppStatus getCached()
    {
        long curr = System.currentTimeMillis();
        if (cached == null || curr - timestamp > 30 * 60 * 1000)
        {
            cached = new CachedAppStatus();
            cached.syncStatus();
            timestamp = curr;
        }
        return cached;
    }    

    public AppStatus getAppStatus()
    {
        AppStatus appStatus = new AppStatus();
        appStatus.setQueryCount(queryCount);
        appStatus.setVisitorCount(visitorCount);
        appStatus.setIndexStatus(SearchEngineService.getInstance().getIndexStatus());
        return appStatus;
    }
    
    private void syncStatus()
    {
        VisitorInfoDao visitorInfoDao = new VisitorInfoDao();
        visitorCount = visitorInfoDao.getVisitorCount();
        
        QueryInfoDao queryInfoDao = new QueryInfoDao();
        queryCount = queryInfoDao.getQueryCount();
    }
}
