/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import org.kuokuo.client.data.QueryResultItem;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * search result item
 */
public class SearchResultItemPanel extends Composite
{
    //VerticalPanel
    public SearchResultItemPanel(QueryResultItem item)
    {
        HorizontalPanel panel = new HorizontalPanel();
        initWidget(panel);
        panel.setSpacing(5);
        panel.add(new HTML("<a href=\""+item.doubanURL+"\"><img border=0 src=\""+item.imageURL+"\"></a>"));
        
        VerticalPanel vBox = new VerticalPanel();
        panel.add(vBox);
        
        {
            HorizontalPanel row = new HorizontalPanel();
            //firstRow.add(new HTML("<img width=\"16\" src=\"folder.gif\">"));
            row.add(new HTML("<b>" + item.getHighlightName() + "</b>"));
            row.add(new HTML("&nbsp;"));
            //row.add(new HTML("<a href=\"http://www.douban.com/subject/"+item.doubanID+"/\">douban</a>"));
            //firstRow.add(new HTML("- <i>" + item.getScore() + "</i>"));
            vBox.add(row);
        }
        
        {
            vBox.add(new Label(item.getLastModified()));
        }

        {
            HorizontalPanel row = new HorizontalPanel();
            row.add(new HTML("<a href=\"" + item.getPath() +  "\" target=\"blank\">" + trimName(item.getPath()) +  "</a>"));
            row.add(new HTML("&nbsp;"));
            vBox.add(row);
        }
    }
    
    private String trimName(String name)
    {
        if(name == null)
        {
            return "...";
        }
        if(name.length() > 80)
        {
            String left = name.substring(0, 10);
            String right = name.substring(name.length() - 77);
            return left + "..." + right;
        }
        return name;
    }
}
