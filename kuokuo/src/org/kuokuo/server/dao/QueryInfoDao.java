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

}
