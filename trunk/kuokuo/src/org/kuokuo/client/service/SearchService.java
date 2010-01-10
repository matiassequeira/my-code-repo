package org.kuokuo.client.service;

import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.client.data.PaginationItem;
import org.kuokuo.client.data.QueryResult;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("search")
public interface SearchService extends RemoteService
{
    public QueryResult query(String name);
    
    public PaginationItem<KuokuoItem> getKuokuoItemOrderByModified(int start, int pageSize);
    
    public PaginationItem<KuokuoItem> getKuokuoItemOrderByModified(String type, int start, int pageSize);
}


