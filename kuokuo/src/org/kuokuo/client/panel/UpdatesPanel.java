/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import java.util.List;
import java.util.Vector;

import org.kuokuo.client.data.KuokuoItem;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
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
    protected TabPanel tabPanel;
    
    protected List<UpdateTabItemPanel> tabItems;
    
    public UpdatesPanel()
    {
        this.setWidth("100%");
        
        tabPanel = new TabPanel();
        tabPanel.setStylePrimaryName("contentTab");
        tabItems = new Vector<UpdateTabItemPanel>();
        
        UpdateTabItemPanel itemPanel = new UpdateTabItemPanel(null);
        itemPanel.queryData(0, 10);
        tabItems.add(itemPanel);
        tabItems.add(new UpdateTabItemPanel(KuokuoItem.TYPE_MOVIE));
        tabItems.add(new UpdateTabItemPanel(KuokuoItem.TYPE_MUSIC));
        tabItems.add(new UpdateTabItemPanel(KuokuoItem.TYPE_GAME));
        tabItems.add(new UpdateTabItemPanel(KuokuoItem.TYPE_BOOK));
        
        
        tabPanel.add(tabItems.get(0), "最近更新");
        tabPanel.add(tabItems.get(1), "电影");
        tabPanel.add(tabItems.get(2), "音乐");
        tabPanel.add(tabItems.get(3), "游戏");
        tabPanel.add(tabItems.get(4), "图书");
        tabPanel.selectTab(0);
        tabPanel.setWidth("100%");
        this.add(tabPanel);

        tabPanel.addSelectionHandler(new SelectionHandler<Integer>()
        {
            
            public void onSelection(SelectionEvent<Integer> event)
            {
                UpdateTabItemPanel itemPanel = tabItems.get(event.getSelectedItem());
                if(itemPanel != null)
                {
                    itemPanel.queryData(0, 10);
                }
            }
        });
    }
}
