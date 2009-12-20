/**
 * 
 */
package org.kuokuo.client.service;

import org.kuokuo.client.data.BookItem;
import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.GameItem;
import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.client.data.MovieItem;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * service for fetch item content
 * 
 * @version Dec 20, 2009 10:13:40 PM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
@RemoteServiceRelativePath("content")
public interface ContentService extends RemoteService
{
    /**
     * get movie item according to kuokuo item
     * 
     * @param item
     * @return
     */
    public MovieItem getMovieItem(KuokuoItem item);

    /**
     * get game item according to kuokuo item
     * 
     * @param item
     * @return
     */
    public GameItem getGameItem(GameItem item);

    /**
     * get book item according to kuokuo item
     * 
     * @param item
     * @return
     */
    public BookItem getBookItem(BookItem item);

    /**
     * get douban resource
     * 
     * @param type
     * @param queryWords
     * @return
     */
    public DoubanResource getDoubanResource(String type, String queryWords);
}
