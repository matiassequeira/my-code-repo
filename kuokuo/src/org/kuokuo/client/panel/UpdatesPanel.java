/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import org.kuokuo.client.data.QueryResult;
import org.kuokuo.client.service.SearchService;
import org.kuokuo.client.service.SearchServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * updates panel
 * 
 * @version Nov 15, 2009 9:47:44 PM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class UpdatesPanel extends Composite
{
    private final SearchServiceAsync searchService = GWT.create(SearchService.class);
    private SearchResultPanel resultPanel;

    public UpdatesPanel()
    {
        VerticalPanel panel = new VerticalPanel();
        initWidget(panel);
        panel.setSpacing(10);
        panel.add(new HTML("<b><i>最近更新</i></b>"));
        resultPanel = new SearchResultPanel();
        panel.add(resultPanel);
        searchService.getUpdateItems(new AsyncCallback<QueryResult>()
        {
            public void onSuccess(QueryResult result)
            {
                resultPanel.bindData(result);
            }

            public void onFailure(Throwable caught)
            {
                resultPanel.bindData(null);
            }
        });
    }
}
