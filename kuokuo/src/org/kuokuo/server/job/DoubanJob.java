/**
 * 
 */
package org.kuokuo.server.job;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.server.job.douban.QueryResource;
import org.kuokuo.server.job.douban.TaskItem;

import com.google.gdata.client.douban.DoubanService;

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
        queryQueue = new ConcurrentLinkedQueue<TaskItem>();

        String apiKey = "0fc141a814ca4f3d2f43c4f83e5eedb7";
        dbService = new DoubanService("kuokuo", apiKey);// read-only service
    }

    private Queue<TaskItem> queryQueue;

    private DoubanService dbService;

    /**
     * push query words into queue
     * 
     * @param type
     * @param queryWords
     */
    public synchronized void offer(TaskItem item)
    {
        queryQueue.offer(item);
        logger.info("offer new task: " + item);
    }

    public void run()
    {
        while (true)
        {
            TaskItem item = queryQueue.poll();
            if (item != null)
            {
                try
                {
                    item.execute(dbService);
                }
                catch (Exception e)
                {
                    logger.warning(e.getMessage());
                }
            }
            try
            {
                Thread.sleep(3500);
            }
            catch (InterruptedException e)
            {
                logger.warning(e.getMessage());
            }
        }
    }

    public static void main(String args[]) throws Exception
    {
        DoubanJob service = DoubanJob.getService();
        Thread.sleep(1000);
        service.offer(new QueryResource(KuokuoItem.TYPE_MOVIE, "蜘蛛侠"));
        service.offer(new QueryResource(KuokuoItem.TYPE_MOVIE, "变形金刚"));
        service.offer(new QueryResource(KuokuoItem.TYPE_MOVIE, "大腕"));
        Thread.sleep(10000);
        service.offer(new QueryResource(KuokuoItem.TYPE_MOVIE, "什么"));
    }
}
