/**
 * 
 */
package org.kuokuo.server.job.douban;

import java.io.IOException;
import java.util.logging.Logger;

import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.server.dao.DoubanResourceDao;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.douban.SubjectEntry;
import com.google.gdata.util.ServiceException;

/**
 * @version Jan 3, 2010 11:37:49 PM
 * @author Dingmeng (xuedm79@gmail.com)
 *
 */
public class UpdateResource implements TaskItem
{
    private static Logger logger = Logger.getLogger(UpdateResource.class.getName());
    
    private DoubanResource resource;
    
    public UpdateResource(DoubanResource resource)
    {
        this.resource = resource;
    }

    /* (non-Javadoc)
     * @see org.kuokuo.server.job.douban.TaskItem#execute(com.google.gdata.client.douban.DoubanService)
     */
    public void execute(DoubanService service) throws IOException, ServiceException
    {
        String type = resource.getType();
        String id = resource.getDoubanId();
        if(id == null)
            return;
        
        SubjectEntry entry = null;
        if (KuokuoItem.TYPE_MOVIE.equals(type))
        {
            entry = service.getMovie(id);
        }
        else if (KuokuoItem.TYPE_MUSIC.equals(type))
        {
            entry = service.getMusic(id);
        }
        else if (KuokuoItem.TYPE_BOOK.equals(type))
        {
            entry = service.getBook(id);
        }

        if(entry == null)
            return;
        
        if(entry.getSummary() != null)
        {
            String summary = entry.getSummary().getPlainText();
            if(summary != null && summary.length() > 1000)
            {
                logger.warning("get summary of " + resource.getTitle() + " > 1000 :" + summary.length());
                summary = summary.substring(0, 999);
            }
            resource.setSummary(summary);
        }

        System.out.println(resource.getSummary());
        DoubanResourceDao dao = new DoubanResourceDao();
        dao.saveOrUpdate(resource);
    }

}
