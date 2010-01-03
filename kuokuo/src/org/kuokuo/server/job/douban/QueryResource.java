/**
 * 
 */
package org.kuokuo.server.job.douban;

import java.io.IOException;
import java.util.List;

import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.server.dao.DoubanResourceDao;
import org.kuokuo.server.job.DoubanJob;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.douban.SubjectEntry;
import com.google.gdata.data.douban.SubjectFeed;
import com.google.gdata.data.extensions.Rating;
import com.google.gdata.util.ServiceException;

/**
 * @version Jan 3, 2010 11:29:47 PM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
public class QueryResource implements TaskItem
{
    private String type;

    private String queryWords;

    public QueryResource(String type, String queryWords)
    {
        this.type = type;
        this.queryWords = queryWords;
    }

    public void execute(DoubanService service) throws IOException, ServiceException
    {
        SubjectFeed feed = null;
        if (KuokuoItem.TYPE_MOVIE.equals(type))
        {
            feed = service.findMovie(queryWords, null, 1, 1);
        }
        else if (KuokuoItem.TYPE_MUSIC.equals(type))
        {
            feed = service.findMusic(queryWords, null, 1, 1);
        }
        else if (KuokuoItem.TYPE_BOOK.equals(type))
        {
            feed = service.findBook(queryWords, null, 1, 1);
        }

        DoubanResource resource;
        List<SubjectEntry> list = feed.getEntries();
        if (list.size() == 0)
        {
            resource = new DoubanResource();
        }
        else
        {
            SubjectEntry entry = list.get(0);
            resource = createDoubanResource(entry, type);
        }
        resource.setQueryWords(queryWords);
        resource.setType(type);

        DoubanJob.getService().offer(new UpdateResource(resource));
        
        // save or update resource
        DoubanResourceDao dao = new DoubanResourceDao();
        dao.saveOrUpdate(resource);
    }

    private DoubanResource createDoubanResource(SubjectEntry entry, String type)
    {
        DoubanResource rv = new DoubanResource();
        rv.setDoubanId(entry.getId());
        rv.setImage(entry.getLink("image", null).getHref());
        rv.setAlternate(entry.getLink("alternate", null).getHref());
        Rating rating = entry.getRating();
        if (rating != null)
        {
            rv.setAverage(rating.getAverage());
            rv.setNumRaters(rating.getNumRaters());
        }
        return rv;
    }
}
