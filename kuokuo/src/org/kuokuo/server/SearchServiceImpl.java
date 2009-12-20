package org.kuokuo.server;

import java.util.List;
import java.util.Vector;

import org.kuokuo.client.data.IndexStatus;
import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.client.data.PaginationItem;
import org.kuokuo.client.data.QueryResult;
import org.kuokuo.client.service.SearchService;
import org.kuokuo.search.SearchEngineService;
import org.kuokuo.server.dao.KuokuoItemDao;

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

    /**
     * @see org.kuokuo.client.service.SearchService#getUpdateItems(int, int)
     */
    public PaginationItem<KuokuoItem> getKuokuoItemOrderByModified(int start, int pageSize)
    {
        try
        {
            KuokuoItemDao dao = new KuokuoItemDao();

            List<KuokuoItem> list = dao.getKuokuoItemOrderByModified(start, pageSize);
            List<KuokuoItem> ret = new Vector<KuokuoItem>(list.size());
            for (KuokuoItem item : list)
            {
                ret.add(item);
            }
            int total = dao.getAllItemsCount();
            PaginationItem<KuokuoItem> rv = new PaginationItem<KuokuoItem>();
            rv.setList(ret);
            rv.setStart(start);
            rv.setPageSize(pageSize);
            rv.setTotal(total);
            return rv;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
