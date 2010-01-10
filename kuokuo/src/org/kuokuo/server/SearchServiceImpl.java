package org.kuokuo.server;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.kuokuo.CachedAppStatus;
import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.client.data.PaginationItem;
import org.kuokuo.client.data.QueryInfo;
import org.kuokuo.client.data.QueryResult;
import org.kuokuo.client.data.VisitorInfo;
import org.kuokuo.client.service.SearchService;
import org.kuokuo.search.SearchEngineService;
import org.kuokuo.server.dao.DoubanResourceDao;
import org.kuokuo.server.dao.KuokuoItemDao;
import org.kuokuo.server.dao.QueryInfoDao;
import org.kuokuo.server.job.DoubanJob;
import org.kuokuo.server.job.douban.QueryResource;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SearchServiceImpl extends AbstractServiceServlet implements SearchService
{

    public QueryResult query(String input)
    {
        try
        {
            QueryInfo queryInfo = new QueryInfo();
            queryInfo.setQueryDate(new Date());
            queryInfo.setQueryWords(input);
            
            HttpSession session = getSession();
            VisitorInfo visitor = (VisitorInfo) session.getAttribute("visitorInfo");
            queryInfo.setVisitor(visitor);
            
            QueryInfoDao dao = new QueryInfoDao();
            dao.saveOrUpdate(queryInfo);
            CachedAppStatus.getCached().increaseQuery();

            QueryResult queryResult = SearchEngineService.getInstance().query(input);
            List<KuokuoItem> list = queryResult.getItems();
            for (KuokuoItem item : list)
            {
                checkDoubanResource(item);
            }
            return queryResult;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new QueryResult();
    }

    /**
     * @see org.kuokuo.client.service.SearchService#getUpdateItems(int, int)
     */
    public PaginationItem<KuokuoItem> getKuokuoItemOrderByModified(int start, int pageSize)
    {
        return getKuokuoItemOrderByModified(null, start, pageSize);
    }

    public PaginationItem<KuokuoItem> getKuokuoItemOrderByModified(String type, int start, int pageSize)
    {
        try
        {
            KuokuoItemDao dao = new KuokuoItemDao();
            List<KuokuoItem> list = dao.getKuokuoItemOrderByModified(type, start, pageSize);
            List<KuokuoItem> ret = new Vector<KuokuoItem>(list.size());
            for (KuokuoItem item : list)
            {
                checkDoubanResource(item);
                ret.add(item);
            }
            int total = dao.getAllItemsCount(type);
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

    private void checkDoubanResource(KuokuoItem item)
    {
        if (item == null || KuokuoItem.TYPE_GAME.equals(item.getType()) || KuokuoItem.TYPE_OTHER.equals(item.getType()))
        {
            return;
        }

        if (item.getDoubanResource() == null)
        {
            DoubanResourceDao doubanResourceDao = new DoubanResourceDao();
            DoubanResource resource = doubanResourceDao.queryResource(item.getType(), item.getName());
            if (resource == null)
            {
                DoubanJob.getService().offer(new QueryResource(item.getType(), item.getName()));
            }
            else
            {
                item.setDoubanResource(resource);

                KuokuoItemDao dao = new KuokuoItemDao();
                dao.saveOrUpdate(item);
            }
        }
    }
}
