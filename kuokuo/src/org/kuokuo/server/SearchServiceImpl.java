package org.kuokuo.server;

import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.DoubanResourceType;
import org.kuokuo.client.data.IndexStatus;
import org.kuokuo.client.data.PagingUpdateItems;
import org.kuokuo.client.data.QueryResult;
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

    public PagingUpdateItems getUpdateItems()
    {
        return getUpdateItems(0, 10);
    }

    public DoubanResource getDoubanInfo(String name, DoubanResourceType type, String cacheKey) throws Exception
    {
        return SearchEngineService.getInstance().loadDataFromDouban(name, type, cacheKey);
    }

    /**
     * @see org.kuokuo.client.service.SearchService#getUpdateItems(int, int)
     */
    public PagingUpdateItems getUpdateItems(int from, int len)
    {
        try
        {
            return SearchEngineService.getInstance().getUpdateItems(from, len);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new PagingUpdateItems();
    }
}