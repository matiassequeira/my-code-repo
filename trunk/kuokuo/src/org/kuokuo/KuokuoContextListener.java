/**
 * 
 */
package org.kuokuo;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;
import org.kuokuo.search.SearchEngineService;
import org.kuokuo.server.dao.HibernateUtil;

/**
 * @author xued
 * 
 */
public class KuokuoContextListener implements ServletContextListener
{
    private static final Logger logger = Logger.getLogger(KuokuoContextListener.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
     * .ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)
    {
        try
        {
            LogManager.getLogManager().readConfiguration(this.getClass().getResourceAsStream("/logging.properties"));
        }
        catch (IOException e)
        {
            new RuntimeException(e);
        }

        // set up mmseg4j dictionary path
        String defPath = System.getProperty("mmseg.dic.path");
        if (defPath == null)
        {
            String dictPath = Configuration.getInstance().getDictPath();
            System.setProperty("mmseg.dic.path", dictPath);
        }

        // set up db path in hibernate
        Session session = HibernateUtil.getSession();
        if (session.isConnected())
        {
            logger.info("database is connected");
        }
        else
        {
            logger.severe("database isn't connected");
        }
        HibernateUtil.closeSession(session);
        
        //start search engine
        SearchEngineService.getInstance().start();
    }

    /*
     * (non-Javadoc)
     * 
     * @seejavax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
     * ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)
    {
        HibernateUtil.closeSessionFactory();
    }
}
