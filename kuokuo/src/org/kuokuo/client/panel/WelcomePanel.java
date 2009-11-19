/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * welcome panel
 * 
 * @version Nov 15, 2009 8:56:23 PM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class WelcomePanel extends HorizontalPanel
{
    
    
    public WelcomePanel()
    {
        this.setHorizontalAlignment(ALIGN_LEFT);
        this.setWidth("1024px");
        this.setSpacing(20);
        
        this.add(new UpdatesPanel());
        
        DecoratorPanel panel = new DecoratorPanel();
        this.add(panel);
        
        panel = new DecoratorPanel();
        this.add(panel);
    }

    /**
     * 
     */
    public void refresh()
    {
        
    }
    
    
}
