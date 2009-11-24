/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.data;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * data for query result
 * 
 * @version Nov 13, 2009 3:58:12 PM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class QueryResult implements IsSerializable
{
    private List<QueryResultItem> items;

    private float time;

    private String query;

    private int count;
    
    /**
     * @return Returns the items.
     */
    public List<QueryResultItem> getItems()
    {
        return items;
    }

    /**
     * @param items
     *            The items to set.
     */
    public void setItems(List<QueryResultItem> items)
    {
        this.items = items;
    }

    /**
     * @return Returns the time.
     */
    public float getTime()
    {
        return time;
    }

    /**
     * @param time
     *            The time to set.
     */
    public void setTime(float time)
    {
        this.time = time;
    }

    /**
     * @return Returns the query.
     */
    public String getQuery()
    {
        return query;
    }

    /**
     * @param query
     *            The query to set.
     */
    public void setQuery(String query)
    {
        this.query = query;
    }

    /**
     * @return Returns the count.
     */
    public int getCount()
    {
        return count;
    }

    /**
     * @param count The count to set.
     */
    public void setCount(int count)
    {
        this.count = count;
    }
}
