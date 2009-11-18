package org.kuokuo.server;

import java.util.List;

import org.kuokuo.client.data.IndexStatus;
import org.kuokuo.client.data.QueryResult;
import org.kuokuo.client.data.QueryResultItem;
import org.kuokuo.client.service.SearchService;
import org.kuokuo.search.SearchEngineService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SearchServiceImpl extends RemoteServiceServlet implements SearchService
{

    public QueryResult query(String input)
    {
        try
        {
            return SearchEngineService.getInstance().query(input);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new QueryResult();
    }

    /**
     * @see org.kuokuo.client.service.SearchService#getIndexStatus()
     */
    public IndexStatus getIndexStatus()
    {
        return SearchEngineService.getInstance().getIndexStatus();
    }
    
    public List<QueryResultItem> getUpdateItems()
    {
        return SearchEngineService.getInstance().getUpdateItems();
    }
}
