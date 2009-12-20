/**
 * 
 */
package org.kuokuo.server.job;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.server.dao.DoubanResourceDao;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.douban.SubjectEntry;
import com.google.gdata.data.douban.SubjectFeed;
import com.google.gdata.util.ServiceException;

/**
 * @version Dec 20, 2009 11:10:08 PM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
public class DoubanJob implements Runnable
{
    private static Logger logger = Logger.getLogger(DoubanJob.class.getName());

    private static DoubanJob instance;

    public static DoubanJob getService()
    {
        if (instance == null)
        {
            instance = new DoubanJob();
            new Thread(instance).start();
        }
        return instance;
    }

    private DoubanJob()
    {
        queryQueue = new ConcurrentLinkedQueue<String[]>();

        String apiKey = "0fc141a814ca4f3d2f43c4f83e5eedb7";
        dbService = new DoubanService("kuokuo", apiKey);// read-only service
    }

    private Queue<String[]> queryQueue;

    private DoubanService dbService;

    /**
     * push query words into queue
     * 
     * @param type
     * @param queryWords
     */
    public synchronized void offer(String type, String queryWords)
    {
        String[] entry =
        { type, queryWords };
        queryQueue.offer(entry);
        logger.info("offer [" + type + "]" + queryWords);
    }

    public void run()
    {
        while (true)
        {
            String[] entry = queryQueue.poll();
            if (entry != null && entry.length >= 2)
            {
                try
                {
                    DoubanResource resource = findDoubanResource(entry[0], entry[1]);
                    logger.info("found " + resource.getQueryWords() + " " + resource.getAlternate());
                    
                    DoubanResourceDao dao = new DoubanResourceDao();
                    dao.saveOrUpdate(resource);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            try
            {
                Thread.sleep(6000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private DoubanResource findDoubanResource(String type, String queryWords) throws IOException, ServiceException
    {
        SubjectFeed feed = null;
        if (KuokuoItem.TYPE_MOVIE.equals(type))
        {
            feed = dbService.findMovie(queryWords, null, 1, 1);
        }
        else if (KuokuoItem.TYPE_MUSIC.equals(type))
        {
            feed = dbService.findMusic(queryWords, null, 1, 1);
        }
        else if (KuokuoItem.TYPE_BOOK.equals(type))
        {
            feed = dbService.findBook(queryWords, null, 1, 1);
        }

        DoubanResource ret;
        List<SubjectEntry> list = feed.getEntries();
        if (list.size() == 0)
        {
            ret = new DoubanResource();
            return ret;
        }
        else
        {
            SubjectEntry entry = list.get(0);
            ret = createDoubanResource(entry, type);
        }
        ret.setQueryWords(queryWords);
        ret.setType(type);
        return ret;
    }

    private DoubanResource createDoubanResource(SubjectEntry entry, String type)
    {
        DoubanResource rv = new DoubanResource();
        rv.setImage(entry.getLink("image", null).getHref());
        // rv.id=entry.getId();
        rv.setAlternate(entry.getLink("alternate", null).getHref());

        return rv;
    }

    public static void main(String args[]) throws Exception
    {
        DoubanJob service = DoubanJob.getService();
        Thread.sleep(1000);
        service.offer(KuokuoItem.TYPE_MOVIE, "蜘蛛侠");
        service.offer(KuokuoItem.TYPE_MOVIE, "变形金刚");
        service.offer(KuokuoItem.TYPE_MOVIE, "大腕");
        Thread.sleep(10000);
        service.offer(KuokuoItem.TYPE_MOVIE, "什么");
    }
}
