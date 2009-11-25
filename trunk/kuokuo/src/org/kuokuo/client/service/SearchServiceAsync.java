package org.kuokuo.client.service;

import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.DoubanResourceType;
import org.kuokuo.client.data.IndexStatus;
import org.kuokuo.client.data.PagingUpdateItems;
import org.kuokuo.client.data.QueryResult;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client side stub for the RPC service.
 */
public interface SearchServiceAsync
{
    public void query(String name, AsyncCallback<QueryResult> callback);
    
    public void getIndexStatus(AsyncCallback<IndexStatus> callback);
    
    public void getUpdateItems(AsyncCallback<PagingUpdateItems> callback);
    
    public void getUpdateItems(int from, int len, AsyncCallback<PagingUpdateItems> callback);
    
    public void getDoubanInfo(String name, DoubanResourceType type, String cacheKey, AsyncCallback<DoubanResource> callback);
}


