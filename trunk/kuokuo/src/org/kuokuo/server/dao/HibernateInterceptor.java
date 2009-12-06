/**
 * 
 */
package org.kuokuo.server.dao;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

/**
 * @version Dec 6, 2009 9:10:08 PM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
public class HibernateInterceptor extends EmptyInterceptor
{
    private static final long serialVersionUID = 8200656727237238655L;

    private String currentUser;

    public HibernateInterceptor(AbstractDao dao)
    {
        currentUser = dao.getUser();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.EmptyInterceptor#onSave(java.lang.Object,
     * java.io.Serializable, java.lang.Object[], java.lang.String[],
     * org.hibernate.type.Type[])
     */
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
    {
        int len = propertyNames.length;
        for (int i = 0; i < len; i++)
        {
            if ("createUser".equals(propertyNames[i]) && state[i] == null)
            {
                    state[i] = currentUser;
            }
            if ("createDatetime".equals(propertyNames[i]) && state[i] == null)
            {
                state[i] = new Date();
            }
            if ("modifyUser".equals(propertyNames[i]))
            {
                state[i] = currentUser;
            }
            if ("modifyDatetime".equals(propertyNames[i]))
            {
                state[i] = new Date();
            }
        }
        return true;
    }

}
