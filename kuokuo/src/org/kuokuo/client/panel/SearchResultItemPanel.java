/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import org.kuokuo.client.data.QueryResultItem;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * search result item
 */
public class SearchResultItemPanel extends Composite
{
    public SearchResultItemPanel(QueryResultItem item)
    {
        buildContent(item);
    }
    
    protected void buildContent(QueryResultItem item)
    {
        VerticalPanel panel = new VerticalPanel();
        initWidget(panel);
        panel.setSpacing(5);

        HorizontalPanel firstRow = new HorizontalPanel();
        firstRow.add(new HTML("&nbsp;"));
        firstRow.add(new HTML("<b>" + item.getHighlightName() + "</b>"));
        firstRow.add(new HTML("&nbsp;"));
        firstRow.add(new HTML("- <i>" + item.getScore() + "</i>"));
        panel.add(firstRow);

        HorizontalPanel secondRow = new HorizontalPanel();
        secondRow.add(new HTML("<a href=\"" + item.getPath() +  "\" target=\"blank\">" + trimName(item.getPath()) +  "</a>"));
        secondRow.add(new HTML("&nbsp;"));
        secondRow.add(new HTML(item.getLastModified()));
        panel.add(secondRow);
    }
    
    protected String trimName(String name)
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
