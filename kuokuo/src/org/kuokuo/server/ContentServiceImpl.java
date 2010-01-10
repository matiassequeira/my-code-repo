/**
 * 
 */
package org.kuokuo.server;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kuokuo.CachedAppStatus;
import org.kuokuo.client.data.AppStatus;
import org.kuokuo.client.data.BookItem;
import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.GameItem;
import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.client.data.MovieItem;
import org.kuokuo.client.data.VisitorInfo;
import org.kuokuo.client.service.ContentService;
import org.kuokuo.server.dao.DoubanResourceDao;
import org.kuokuo.server.dao.VisitorInfoDao;
import org.kuokuo.server.job.DoubanJob;
import org.kuokuo.server.job.douban.QueryResource;

/**
 * @version Dec 20, 2009 10:21:56 PM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
public class ContentServiceImpl extends AbstractServiceServlet implements ContentService
{
    /**
     * 
     */
    private static final long serialVersionUID = 2816815257938023507L;

    public BookItem getBookItem(BookItem item)
    {
        return null;
    }

    public DoubanResource getDoubanResource(String type, String queryWords)
    {
        if(queryWords == null)
        {
            return null;
        }
        
        DoubanResourceDao dao = new DoubanResourceDao();
        DoubanResource resource = dao.queryResource(type, queryWords);
        if(resource == null)
        {
            DoubanJob.getService().offer(new QueryResource(type, queryWords));
        }
        else
        {
            return resource;
        }
        
        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        
        resource = dao.queryResource(type, queryWords);
        return resource;
    }

    public GameItem getGameItem(GameItem item)
    {
        return null;
    }

    public MovieItem getMovieItem(KuokuoItem item)
    {
        return null;
    }

    public AppStatus getAppStatus()
    {
        HttpSession session = getSession();
        VisitorInfo visitorInfo = (VisitorInfo) session.getAttribute("visitorInfo");
        if(visitorInfo == null)
        {
            visitorInfo = new VisitorInfo();
            HttpServletRequest request = getThreadLocalRequest();
            visitorInfo.setRemoteAddr(request.getRemoteAddr());
            visitorInfo.setRemoteHost(request.getRemoteHost());
            visitorInfo.setRemoteUser(request.getRemoteUser());
            visitorInfo.setUserAgent(request.getHeader("User-Agent"));
            visitorInfo.setFirstVisit(new Date());
            
            CachedAppStatus.getCached().increaseVisitor();
        }
        visitorInfo.setLastVisit(new Date());

        VisitorInfoDao dao = new VisitorInfoDao();
        dao.saveOrUpdate(visitorInfo);
        
        session.setAttribute("visitorInfo", visitorInfo);

        
        AppStatus status = CachedAppStatus.getCached().getAppStatus();
        
        
        return status;
    }
}
