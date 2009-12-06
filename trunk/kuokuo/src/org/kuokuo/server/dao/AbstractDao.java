/**
 * 
 */
package org.kuokuo.server.dao;

import org.hibernate.Session;

/**
 * @version Dec 6, 2009 9:06:41 PM
 * @author Dingmeng (xuedm79@gmail.com)
 *
 */
public abstract class AbstractDao
{
    private String user;
    
    public AbstractDao()
    {
        this("system");
    }
    
    public AbstractDao(String user)
    {
        this.user = (user == null) ? "default" : user;
    }
    
    /**
     * get current user in data access object
     * 
     * @return
     */
    public String getUser()
    {
        return this.user;
    }
    
    protected Session getSession()
    {
        return HibernateUtil.getSession(this);
    }
    
    protected void releaseSession(Session session)
    {
        HibernateUtil.closeSession(session);
    }
}
