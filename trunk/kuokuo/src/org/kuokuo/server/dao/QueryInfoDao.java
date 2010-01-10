/**
 * 
 */
package org.kuokuo.server.dao;

import org.hibernate.Session;
import org.kuokuo.client.data.QueryInfo;

/**
 * @version Jan 10, 2010 12:43:42 AM
 * @author Dingmeng (xuedm79@gmail.com)
 *
 */
public class QueryInfoDao extends AbstractDao
{
    public QueryInfo saveOrUpdate(QueryInfo queryInfo)
    {
        Session session = getSession();
        try
        {
            session.beginTransaction();
            session.saveOrUpdate(queryInfo);
            session.getTransaction().commit();
            return queryInfo;
        }
        finally
        {
            releaseSession(session);
        }
    }
    
    /**
     * get visitor count
     * 
     * @return
     */
    public long getQueryCount()
    {
        long count = 0;
        Session session = getSession();
        try
        {
            String hql = "select count(queryInfo.id) from QueryInfo as queryInfo";
            count = ((Long) (session.createQuery(hql).iterate().next())).longValue();
            return count;
        }
        finally
        {
            releaseSession(session);
        }
    }

}
