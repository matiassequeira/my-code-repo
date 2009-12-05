/**
 * 
 */
package org.kuokuo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author xued
 *
 */
public class KuokuoContextListener implements ServletContextListener
{

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)
    {
        //set up mmseg4j dictionary path
        String defPath = System.getProperty("mmseg.dic.path");
        if(defPath == null)
        {
            String dictPath = Configuration.getInstance().getDictPath();
            System.setProperty("mmseg.dic.path", dictPath);
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)
    {
        
    }
}
