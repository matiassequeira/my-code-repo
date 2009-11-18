package org.kuokuo.client.service;

import java.util.List;

import org.kuokuo.client.data.IndexStatus;
import org.kuokuo.client.data.QueryResult;
import org.kuokuo.client.data.QueryResultItem;

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
    
    public List<QueryResultItem> getUpdateItems();
}
