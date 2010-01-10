package org.kuokuo.client.service;

import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.client.data.PaginationItem;
import org.kuokuo.client.data.QueryResult;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client side stub for the RPC service.
 */
public interface SearchServiceAsync
{
    public void query(String name, AsyncCallback<QueryResult> callback);
    
    public void getKuokuoItemOrderByModified(int start, int pageSize, AsyncCallback<PaginationItem<KuokuoItem>> callback);
    
    public void getKuokuoItemOrderByModified(String type, int start, int pageSize, AsyncCallback<PaginationItem<KuokuoItem>> callback);
}


