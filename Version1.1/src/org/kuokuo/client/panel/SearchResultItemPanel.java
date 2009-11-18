/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import org.kuokuo.client.data.QueryResultItem;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * search result item
 * 
 * @version Nov 15, 2009 9:37:54 AM
 * @author Dingmeng Xue (xue_dingmeng@emc.com)
 */
public class SearchResultItemPanel extends VerticalPanel
{
    public SearchResultItemPanel(QueryResultItem item)
    {
        this.setSpacing(3);
        
        HorizontalPanel firstRow = new HorizontalPanel();
        //firstRow.add(new HTML("<img width=\"16\" src=\"folder.gif\">"));
        firstRow.add(new HTML("&nbsp;"));
        firstRow.add(new HTML("<b>" + item.getHighlightName() + "</b>"));
        firstRow.add(new HTML("&nbsp;"));
        firstRow.add(new HTML("- <i>" + item.getScore() + "</i>"));
        this.add(firstRow);

        HorizontalPanel secondRow = new HorizontalPanel();
        secondRow.add(new HTML("<a href=\"" + item.getPath() +  "\" target=\"blank\">" + trimName(item.getPath()) +  "</a>"));
        secondRow.add(new HTML("&nbsp;"));
        secondRow.add(new HTML(item.getLastModified()));
        this.add(secondRow);
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
