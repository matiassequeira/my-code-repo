/**
 * 
 */
package org.kuokuo.server.dao;

import org.hibernate.Session;
import org.kuokuo.client.data.VisitorInfo;

/**
 * @version Jan 10, 2010 12:27:23 AM
 * @author Dingmeng (xuedm79@gmail.com)
 *
 */
public class VisitorInfoDao extends AbstractDao
{
    /**
     * save or update visitor info
     * 
     * @param resource
     * @return
     */
    public VisitorInfo saveOrUpdate(VisitorInfo visitorInfo)
    {
        Session session = getSession();
        try
        {
            session.beginTransaction();
            session.saveOrUpdate(visitorInfo);
            session.getTransaction().commit();
            return visitorInfo;
        }
        finally
        {
            releaseSession(session);
        }
    }

}
