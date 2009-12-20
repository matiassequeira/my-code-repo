/**
 * 
 */
package org.kuokuo.server.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.kuokuo.client.data.DoubanResource;

/**
 * @version Dec 16, 2009 11:52:33 PM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
public class DoubanResourceDao extends AbstractDao
{
    /**
     * save or update douban item
     * 
     * @param resource
     * @return
     */
    public DoubanResource saveOrUpdate(DoubanResource resource)
    {
        Session session = getSession();
        try
        {
            session.beginTransaction();
            session.saveOrUpdate(resource);
            session.getTransaction().commit();
            return resource;
        }
        finally
        {
            releaseSession(session);
        }
    }

    @SuppressWarnings("unchecked")
    public DoubanResource queryResource(String type, String queryWords)
    {
        Session session = getSession();
        try
        {
            Criteria crit = session.createCriteria(DoubanResource.class);
            crit.add(Restrictions.eq("type", type));
            crit.add(Restrictions.eq("queryWords", queryWords));
            crit.setMaxResults(1);
            List<DoubanResource> list = crit.list();
            if (list.size() > 0)
                return list.get(0);
            else
                return null;
        }
        finally
        {
            releaseSession(session);
        }
    }

    /**
     * remove obsolete resource
     * 
     * @param obsoleteDatetime
     * @return
     */
    public int removeObsoleteResource(Date obsoleteDatetime)
    {
        Session session = getSession();

        try
        {
            session.beginTransaction();
            Query query = session.createQuery("delete from DoubanResource where createDatetime < :obsoleteDatetime");
            query = query.setParameter("obsoleteDatetime", obsoleteDatetime);
            int ret = query.executeUpdate();
            session.getTransaction().commit();
            return ret;
        }
        finally
        {
            releaseSession(session);
        }
    }
}
