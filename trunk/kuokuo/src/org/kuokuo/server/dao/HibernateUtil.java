/**
 * 
 */
package org.kuokuo.server.dao;

import java.util.Properties;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * @author Felix
 * 
 */
public class HibernateUtil
{
    private static final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(HibernateUtil.class.getName());
    
    static
    {
        try
        {
            Configuration config = new AnnotationConfiguration().configure();
            Properties props = config.getProperties();
            if (props.getProperty("connection.url") == null)
            {
                String url = props.getProperty("connection.ext.h2_db_path");
                url = org.kuokuo.Configuration.getInstance().getPathUnderWebInfo(url);
                url = "jdbc:h2:file:" + url;
                config.setProperty("connection.url", url);
                config.setProperty("hibernate.connection.url", url);
            }
            sessionFactory = config.buildSessionFactory();
        }
        catch (Throwable ex)
        {
            logger.severe(ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException
    {
        return sessionFactory.openSession();
    }
    
    public static Session getSession(AbstractDao dao) throws HibernateException
    {
        return sessionFactory.openSession(new HibernateInterceptor(dao));
    }

    public static void closeSession(Session session) throws HibernateException
    {
        session.close();
    }

    public static void closeSessionFactory() throws HibernateException
    {
        sessionFactory.close();
    }
}
