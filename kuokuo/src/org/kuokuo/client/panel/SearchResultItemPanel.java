/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import org.kuokuo.client.data.KuokuoItem;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * search result item
 */
public class SearchResultItemPanel extends Composite
{
    public SearchResultItemPanel(KuokuoItem item)
    {
        buildContent(item);
    }
    
    protected SimplePanel thumbnail;
    
    protected RatingPanel ratingPanel;
    
    protected void buildContent(KuokuoItem item)
    {
        HorizontalPanel mainPanel = new HorizontalPanel();
        initWidget(mainPanel);
        
        thumbnail = buildThumbnail();
        mainPanel.add(thumbnail);
        
        VerticalPanel fpanel = new VerticalPanel();
        fpanel.setSpacing(5);

        HorizontalPanel row = new HorizontalPanel();
        if(item.getHighlightName() == null)
        {
            row.add(new HTML("<b>" + item.getName() + "</b>"));
        }
        else
        {
            row.add(new HTML("<b>" + item.getHighlightName() + "</b>"));
        }
        row.add(new HTML("&nbsp;"));
        if(item.getScore() > 0)
        {
            row.add(new HTML("- <i>" + item.getScore() + "</i>"));
        }
        fpanel.add(row);

        ratingPanel = new RatingPanel();
        fpanel.add(ratingPanel);
        
        Panel[] extPanels = buildExtPanels(item);
        if(extPanels != null)
        {
            for (Panel panel : extPanels)
            {
                fpanel.add(panel);
            }
        }
        
        row = new HorizontalPanel();
        String str = "更新时间：" + DateTimeFormat.getShortDateTimeFormat().format(item.getLastModified());
        if(!item.isFolder())
        {
            str = str + "　 文件大小：" + NumberFormat.getFormat("#,###.###").format((double)item.getLength()/1000) + "K";
        }
        HTML html = new HTML(str);
        html.setStyleName("item-gray");
        row.add(html);
        fpanel.add(row);

        row = new HorizontalPanel();
        row.add(new HTML("<a href=\"" + item.getPath() +  "\" target=\"blank\">" + trimName(item.getPath(), 80) +  "</a>"));
        fpanel.add(row);

        mainPanel.add(fpanel);
    }

    protected SimplePanel buildThumbnail()
    {
        return new SimplePanel();
    }
    
    protected Panel[] buildExtPanels(KuokuoItem item)
    {
        return null;
    }
    
    protected String trimName(String name, int length)
    {
        if(name == null)
        {
            return "...";
        }
        if(name.length() > length)
        {
            String left = name.substring(0, 10);
            String right = name.substring(name.length() - (length - 13));
            return left + "..." + right;
        }
        return name;
    }
}
