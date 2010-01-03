/**
 * 
 */
package org.kuokuo.server;

import org.kuokuo.client.data.BookItem;
import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.GameItem;
import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.client.data.MovieItem;
import org.kuokuo.client.service.ContentService;
import org.kuokuo.server.dao.DoubanResourceDao;
import org.kuokuo.server.job.DoubanJob;
import org.kuokuo.server.job.douban.QueryResource;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @version Dec 20, 2009 10:21:56 PM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
public class ContentServiceImpl extends RemoteServiceServlet implements ContentService
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

}
