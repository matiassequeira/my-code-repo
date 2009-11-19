/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.data;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * index status
 * 
 * @version Nov 15, 2009 12:00:23 PM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class IndexStatus implements IsSerializable
{
    private long indexCost = 0;

    private long docCount = 0;

    private String lastUpdate;

    private long queryCount = 0;

    private String startDate;
    
    
    
    /**
     * @return Returns the startDate.
     */
    public String getStartDate()
    {
        return startDate;
    }

    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    /**
     * @return Returns the indexCost.
     */
    public long getIndexCost()
    {
        return indexCost;
    }

    /**
     * @param indexCost
     *            The indexCost to set.
     */
    public void setIndexCost(long indexCost)
    {
        this.indexCost = indexCost;
    }

    /**
     * @return Returns the docCount.
     */
    public long getDocCount()
    {
        return docCount;
    }

    /**
     * @param docCount
     *            The docCount to set.
     */
    public void setDocCount(long docCount)
    {
        this.docCount = docCount;
    }

    /**
     * @return Returns the lastUpdate.
     */
    public String getLastUpdate()
    {
        return lastUpdate;
    }

    /**
     * @param lastUpdate
     *            The lastUpdate to set.
     */
    public void setLastUpdate(String lastUpdate)
    {
        this.lastUpdate = lastUpdate;
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
    public synchronized void setQueryCount(long queryCount)
    {
        this.queryCount = queryCount;
    }

}
