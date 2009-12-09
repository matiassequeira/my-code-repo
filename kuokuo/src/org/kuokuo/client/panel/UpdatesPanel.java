/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import java.util.List;

import org.kuokuo.client.Search;
import org.kuokuo.client.ServiceFactory;
import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.client.data.PaginationItem;
import org.kuokuo.client.service.SearchServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * updates panel
 * 
 * @version Nov 15, 2009 9:47:44 PM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class UpdatesPanel extends VerticalPanel
{
    protected VerticalPanel contentPanel;

    protected HorizontalPanel footerPanel;

    protected PaginationPanel pagingPanel1;
    
    protected PaginationPanel pagingPanel2;
    
    public UpdatesPanel()
    {
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
        
        TabPanel tabPanel = new TabPanel();
        contentPanel = new VerticalPanel();
        contentPanel.setSpacing(5);
        tabPanel.add(contentPanel, "最近更新");
        tabPanel.selectTab(0);
        tabPanel.setWidth("100%");
        this.add(tabPanel);
        this.setCellWidth(tabPanel, "80%");
        this.setCellHorizontalAlignment(contentPanel, ALIGN_LEFT);

        footerPanel = new HorizontalPanel();
        this.add(footerPanel);
        this.setCellHorizontalAlignment(footerPanel, ALIGN_RIGHT);
        queryData(0, 10);
    }

    private void queryData(int start, int pageSize)
    {
        SearchServiceAsync searchService = ServiceFactory.SERVICE_SEARCH;
        searchService.getKuokuoItemOrderByModified(start, pageSize, new AsyncCallback<PaginationItem<KuokuoItem>>()
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
        contentPanel.clear();
        pagingPanel1.bindData(start, total);
        contentPanel.add(pagingPanel1);
        List<KuokuoItem> list = items.getList();
        
        for(KuokuoItem item : list)
        {
            Composite resultItem = null;
            if(Search.TYPE_MOVIE.equals(item.getType()))
            {
                resultItem = new MovieItemPanel(item);
            }
            else if(Search.TYPE_MUSIC.equals(item.getType()))
            {
                resultItem = new MusicItemPanel(item);
            }
            else
            {
                resultItem = new SearchResultItemPanel(item);
            }
            contentPanel.add(resultItem);
        }
        pagingPanel2.bindData(start, total);
        contentPanel.add(pagingPanel2);
    }
}
