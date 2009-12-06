/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import java.util.List;

import org.kuokuo.client.Search;
import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.client.data.QueryResult;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * search result
 * 
 * @version Nov 15, 2009 9:55:30 AM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class SearchResultPanel extends VerticalPanel
{
    protected HTML resultStatus;

    protected HorizontalPanel titleRow;

    protected VerticalPanel resultRow;

    public SearchResultPanel()
    {
        this.setHorizontalAlignment(ALIGN_LEFT);
        this.setWidth("1024px");
        titleRow = new HorizontalPanel();
        resultStatus = new HTML();
        titleRow.add(resultStatus);
        titleRow.setCellHorizontalAlignment(resultStatus, ALIGN_RIGHT);
        this.add(titleRow);
        this.setCellHorizontalAlignment(titleRow, ALIGN_RIGHT);
        
        resultRow = new VerticalPanel();
        resultRow.setSpacing(20);
        this.add(resultRow);
    }

    public void bindData(QueryResult result)
    {
        List<KuokuoItem> resultItems = result.getItems();
        resultRow.clear();
        for (KuokuoItem item : resultItems)
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
            resultRow.add(resultItem);
        }

        String status = "找到相关对象" + resultItems.size() + "个，用时" + result.getTime() + "秒";
        resultStatus.setText(status);
    }
}
