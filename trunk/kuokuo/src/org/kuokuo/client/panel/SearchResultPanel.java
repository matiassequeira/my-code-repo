/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import java.util.List;

import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.client.data.QueryResult;
import org.kuokuo.client.widget.HomeButton;

import com.google.gwt.user.client.ui.Button;
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
        this.setWidth("100%");
        
        titleRow = new HorizontalPanel();
        resultStatus = new HTML();
        titleRow.add(resultStatus);
        titleRow.setCellHorizontalAlignment(resultStatus, ALIGN_RIGHT);
        this.add(titleRow);
        this.setCellHorizontalAlignment(titleRow, ALIGN_RIGHT);
        
        resultRow = new VerticalPanel();
        resultRow.setWidth("100%");
        this.add(resultRow);
        
        Button btnReturn = new HomeButton();
        this.add(btnReturn);
        this.setCellHorizontalAlignment(btnReturn, ALIGN_CENTER);
    }

    public void bindData(QueryResult result)
    {
        List<KuokuoItem> resultItems = result.getItems();
        
        boolean first = true;
        
        resultRow.clear();
        for (KuokuoItem item : resultItems)
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
            
            if(resultItem != null)
            {
                if(first)
                {
                    first = false;
                }
                else
                {
                    HTML spacer = new HTML("<hr>");
                    spacer.setStylePrimaryName("content-space");
                    resultRow.add(spacer);
                }
                resultRow.add(resultItem);
            }
        }

        String status = "找到相关对象" + resultItems.size() + "个，用时" + result.getTime() + "秒";
        resultStatus.setText(status);
    }
}
