package org.kuokuo.client.service;

import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.DoubanResourceType;
import org.kuokuo.client.data.IndexStatus;
import org.kuokuo.client.data.PagingUpdateItems;
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
    
    public IndexStatus getIndexStatus();
    
    public PagingUpdateItems getUpdateItems();
    
    public PagingUpdateItems getUpdateItems(int from, int len);
    
    public DoubanResource getDoubanInfo(String name, DoubanResourceType type, String cacheKey) throws Exception;
}


