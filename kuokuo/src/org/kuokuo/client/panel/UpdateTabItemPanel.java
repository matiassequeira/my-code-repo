/**
 * 
 */
package org.kuokuo.client.panel;

import java.util.List;

import org.kuokuo.client.ServiceFactory;
import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.client.data.PaginationItem;
import org.kuokuo.client.service.SearchServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @version Dec 23, 2009 10:34:25 PM
 * @author Dingmeng (xuedm79@gmail.com)
 *
 */
public class UpdateTabItemPanel extends VerticalPanel
{
    private String type;
    
    public UpdateTabItemPanel(String type)
    {
        this.type = type;
        this.setSpacing(5);
        pagingPanel1 = new PaginationPanel()
        {
            public void page(int start, int pageSize)
            {
                queryData(start, pageSize);
            }
        };
        pagingPanel2 = new PaginationPanel()
        {
            public void page(int start, int pageSize)
            {
                queryData(start, pageSize);
            }
        };
    }
    
    protected PaginationPanel pagingPanel1;
    
    protected PaginationPanel pagingPanel2;

    public void queryData(int start, int pageSize)
    {
        SearchServiceAsync searchService = ServiceFactory.SERVICE_SEARCH;
        searchService.getKuokuoItemOrderByModified(type, start, pageSize, new AsyncCallback<PaginationItem<KuokuoItem>>()
        {
            public void onSuccess(PaginationItem<KuokuoItem> items)
            {
                bindData(items);
            }

            public void onFailure(Throwable caught)
            {

            }
        });
    }
    
    protected void bindData(PaginationItem<KuokuoItem> items)
    {
        int start = items.getStart();
        int total = items.getTotal();
        this.clear();
        pagingPanel1.bindData(start, total);
        this.add(pagingPanel1);
        List<KuokuoItem> list = items.getList();
        
        for(KuokuoItem item : list)
        {
            Composite resultItem = null;
            if(KuokuoItem.TYPE_MOVIE.equals(item.getType()))
            {
                resultItem = new MovieItemPanel(item);
            }
            else if(KuokuoItem.TYPE_MUSIC.equals(item.getType()))
            {
                resultItem = new MusicItemPanel(item);
            }
            else
            {
                resultItem = new SearchResultItemPanel(item);
            }
            this.add(resultItem);
        }
        pagingPanel2.bindData(start, total);
        this.add(pagingPanel2);
    }
}
