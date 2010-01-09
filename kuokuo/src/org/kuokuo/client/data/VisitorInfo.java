/**
 * 
 */
package org.kuokuo.client.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @version Jan 10, 2010 12:15:53 AM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
@Entity
@Table(name = "VISITOR_INFO")
public class VisitorInfo extends PersistentObject
{
    private static final long serialVersionUID = -6962500850617922113L;

    @Column(name = "FIRST_VISIT")
    private Date firstVisit;

    @Column(name = "LAST_VISIT")
    private Date lastVisit;

    @Column(name = "REMOTE_ADDR", length = 50)
    private String remoteAddr;

    @Column(name = "REMOTE_HOST", length = 50)
    private String remoteHost;

    @Column(name = "REMOTE_USER", length = 50)
    private String remoteUser;

    @Column(name = "USER_AGENT", length = 250)
    private String userAgent;

    /**
     * @return the firstVisit
     */
    public Date getFirstVisit()
    {
        return firstVisit;
    }

    /**
     * @param firstVisit
     *            the firstVisit to set
     */
    public void setFirstVisit(Date firstVisit)
    {
        this.firstVisit = firstVisit;
    }

    /**
     * @return the lastVisit
     */
    public Date getLastVisit()
    {
        return lastVisit;
    }

    /**
     * @param lastVisit
     *            the lastVisit to set
     */
    public void setLastVisit(Date lastVisit)
    {
        this.lastVisit = lastVisit;
    }

    /**
     * @return the remoteAddr
     */
    public String getRemoteAddr()
    {
        return remoteAddr;
    }

    /**
     * @param remoteAddr
     *            the remoteAddr to set
     */
    public void setRemoteAddr(String remoteAddr)
    {
        this.remoteAddr = remoteAddr;
    }

    /**
     * @return the remoteHost
     */
    public String getRemoteHost()
    {
        return remoteHost;
    }

    /**
     * @param remoteHost
     *            the remoteHost to set
     */
    public void setRemoteHost(String remoteHost)
    {
        this.remoteHost = remoteHost;
    }

    /**
     * @return the remoteUser
     */
    public String getRemoteUser()
    {
        return remoteUser;
    }

    /**
     * @param remoteUser
     *            the remoteUser to set
     */
    public void setRemoteUser(String remoteUser)
    {
        this.remoteUser = remoteUser;
    }

    /**
     * @return the userAgent
     */
    public String getUserAgent()
    {
        return userAgent;
    }

    /**
     * @param userAgent
     *            the userAgent to set
     */
    public void setUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }
}
