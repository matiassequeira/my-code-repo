/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import java.util.List;

import org.kuokuo.client.data.QueryResultItem;
import org.kuokuo.client.service.SearchService;
import org.kuokuo.client.service.SearchServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * updates panel
 * 
 * @version Nov 15, 2009 9:47:44 PM
 * @author Dingmeng Xue (xue_dingmeng@emc.com)
 */
public class UpdatesPanel extends VerticalPanel
{
    private final SearchServiceAsync searchService = GWT.create(SearchService.class);

    public UpdatesPanel()
    {
        this.setSpacing(10);
        searchService.getUpdateItems(new AsyncCallback<List<QueryResultItem>>()
        {

            public void onSuccess(List<QueryResultItem> result)
            {
                refresh(result);
            }

            public void onFailure(Throwable caught)
            {
                refresh(null);
            }
        });
    }

    private void refresh(List<QueryResultItem> items)
    {
        this.clear();
        this.add(new HTML("<b><i>最近更新</i></b>"));
        for(QueryResultItem item: items)
        {
            HorizontalPanel row = new HorizontalPanel();
            row.add(new HTML("<a href=\"" + item.getPath() +  "\" target=\"blank\">" + trimName(item.getName()) +  "</a>"));
            row.add(new HTML("&nbsp;"));
            row.add(new HTML(item.getLastModified()));
            this.add(row);
        }
    }
    
    private String trimName(String name)
    {
        if(name == null)
        {
            return "...";
        }
        if(name.length() > 50)
        {
            String left = name.substring(0, 5);
            String right = name.substring(name.length() - 42);
            return left + "..." + right;
        }
        return name;
    }
}
