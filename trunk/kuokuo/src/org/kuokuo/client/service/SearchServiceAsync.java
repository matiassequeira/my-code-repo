package org.kuokuo.client.service;

import java.util.List;

import org.kuokuo.client.data.IndexStatus;
import org.kuokuo.client.data.QueryResult;
import org.kuokuo.client.data.QueryResultItem;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SearchServiceAsync
{
    public void query(String input, AsyncCallback<QueryResult> callback);

    public void getIndexStatus(AsyncCallback<IndexStatus> callback);

    public void getUpdateItems(AsyncCallback<List<QueryResultItem>> callback);
}
