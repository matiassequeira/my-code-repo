/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.data;

import org.kuokuo.client.Search;

/**
 * movie query result item
 * 
 * @version Nov 23, 2009 11:16:30 PM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class MovieResultItem extends QueryResultItem
{

    /**
     * @see org.kuokuo.client.data.QueryResultItem#checkType(java.lang.String)
     */
    @Override
    public boolean checkType(String type)
    {
        return Search.TYPE_MOVIE.equals(type);
    }

}
