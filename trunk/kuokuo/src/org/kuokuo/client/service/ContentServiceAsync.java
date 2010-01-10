/**
 * 
 */
package org.kuokuo.client.service;

import org.kuokuo.client.data.BookItem;
import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.GameItem;
import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.client.data.MovieItem;
import org.kuokuo.client.data.AppStatus;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @version Dec 20, 2009 10:13:51 PM
 * @author Dingmeng (xuedm79@gmail.com)
 *
 */
public interface ContentServiceAsync
{

    void getMovieItem(KuokuoItem item, AsyncCallback<MovieItem> callback);

    void getGameItem(GameItem item, AsyncCallback<GameItem> callback);

    void getBookItem(BookItem item, AsyncCallback<BookItem> callback);

    void getDoubanResource(String type, String queryWords, AsyncCallback<DoubanResource> callback);

    void getAppStatus(AsyncCallback<AppStatus> callback);
}
