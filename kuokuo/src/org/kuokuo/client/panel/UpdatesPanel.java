/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import java.util.List;

import org.kuokuo.client.Search;
import org.kuokuo.client.ServiceFactory;
import org.kuokuo.client.data.PagingUpdateItems;
import org.kuokuo.client.data.QueryResultItem;
import org.kuokuo.client.service.SearchServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
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

    public UpdatesPanel()
    {
        this.setSpacing(10);
        this.add(new HTML("<b><i>最近更新</i></b>"));

        contentPanel = new VerticalPanel();
        this.add(contentPanel);
        this.setCellHorizontalAlignment(contentPanel, ALIGN_LEFT);

        footerPanel = new HorizontalPanel();
        this.add(footerPanel);
        this.setCellHorizontalAlignment(footerPanel, ALIGN_RIGHT);

        SearchServiceAsync searchService = ServiceFactory.SERVICE_SEARCH;
        searchService.getUpdateItems(0, 50, new AsyncCallback<PagingUpdateItems>()
        {
            public void onSuccess(PagingUpdateItems items)
            {
                bindData(items);
            }

            public void onFailure(Throwable caught)
            {

            }
        });
    }

    protected void bindData(PagingUpdateItems items)
    {
        contentPanel.clear();
        List<QueryResultItem> list = items.getItems();
        
        for(QueryResultItem item : list)
        {
            Composite resultItem = null;
            if(item.checkType(Search.TYPE_MOVIE))
            {
                //resultItem = new MovieItemPanel(item);
            }
            else if(item.checkType(Search.TYPE_MUSIC))
            {
                //resultItem = new MusicItemPanel(item);
            }            
            else
            {
                //resultItem = new SearchResultItemPanel(item);
            }
            contentPanel.add(resultItem);
        }
        
        
    }
}
