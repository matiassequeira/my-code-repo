/**
 * 
 */
package org.kuokuo.client.data;

import java.io.Serializable;
import java.util.List;

/**
 * @version Dec 8, 2009 9:12:28 PM
 * @author Dingmeng (xuedm79@gmail.com)
 *
 */
public class PaginationItem<T> implements Serializable
{
    private static final long serialVersionUID = 2325701694607514220L;

    private int start;
    
    private int pageSize;
    
    private int total;
    
    private List<T> list;

    /**
     * @return the start
     */
    public int getStart()
    {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start)
    {
        this.start = start;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    /**
     * @return the totalSize
     */
    public int getTotal()
    {
        return total;
    }

    /**
     * @param totalSize the totalSize to set
     */
    public void setTotal(int total)
    {
        this.total = total;
    }

    /**
     * @return the list
     */
    public List<T> getList()
    {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<T> list)
    {
        this.list = list;
    }
}
