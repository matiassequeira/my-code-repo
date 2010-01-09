/**
 * 
 */
package org.kuokuo.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @version Jan 10, 2010 12:33:11 AM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
public abstract class AbstractServiceServlet extends RemoteServiceServlet
{
    private static final long serialVersionUID = 361229278341396130L;

    protected HttpSession getSession()
    {
        return this.getThreadLocalRequest().getSession();
    }

}
