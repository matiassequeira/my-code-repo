/**
 * 
 */
package org.kuokuo.client.data;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @version Jan 10, 2010 11:39:56 AM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
public class AppStatus implements IsSerializable
{
    private IndexStatus indexStatus;

    private long visitorCount = 0;

    private long queryCount = 0;

    /**
     * @return the indexStatus
     */
    public IndexStatus getIndexStatus()
    {
        return indexStatus;
    }

    /**
     * @param indexStatus
     *            the indexStatus to set
     */
    public void setIndexStatus(IndexStatus indexStatus)
    {
        this.indexStatus = indexStatus;
    }

    /**
     * @return the visitorCount
     */
    public long getVisitorCount()
    {
        return visitorCount;
    }

    /**
     * @param visitorCount
     *            the visitorCount to set
     */
    public void setVisitorCount(long visitorCount)
    {
        this.visitorCount = visitorCount;
    }

    /**
     * @return Returns the queryCount.
     */
    public long getQueryCount()
    {
        return queryCount;
    }

    /**
     * @param queryCount
     *            The queryCount to set.
     */
    public void setQueryCount(long queryCount)
    {
        this.queryCount = queryCount;
    }
}
