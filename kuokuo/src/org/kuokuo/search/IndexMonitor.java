/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.search;

import org.kuokuo.Configuration;

/**
 * index monitor
 * 
 * @version Nov 16, 2009 9:55:43 AM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class IndexMonitor extends Thread
{
    /**
     * @see java.lang.Thread#run()
     */
    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                long interval = Configuration.getInstance().getIndexInterval() * 60 * 1000;
                sleep(interval);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            try
            {
                SearchEngineService.getInstance().reindex();
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
        }
    }

}
