/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.data;

import java.util.List;


import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * updates data for panel
 * 
 * @version Nov 24, 2009 11:13:21 PM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class PagingUpdateItems implements IsSerializable
{
    private List<QueryResultItem> items;

    private int count;

    private int start;

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
     * @return Returns the count.
     */
    public int getCount()
    {
        return count;
    }

    /**
     * @param count
     *            The count to set.
     */
    public void setCount(int count)
    {
        this.count = count;
    }

    /**
     * @return Returns the start.
     */
    public int getStart()
    {
        return start;
    }

    /**
     * @param start The start to set.
     */
    public void setStart(int start)
    {
        this.start = start;
    }
}
