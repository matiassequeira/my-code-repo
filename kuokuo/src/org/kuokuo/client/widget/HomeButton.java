/**
 * 
 */
package org.kuokuo.client.widget;

import org.kuokuo.client.Search;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * @version Jan 9, 2010 12:25:42 PM
 * @author Dingmeng (xuedm79@gmail.com)
 *
 */
public class HomeButton extends Button implements ClickHandler
{
    public HomeButton()
    {
        super("返回");
        this.addClickHandler(this);
    }

    public void onClick(ClickEvent event)
    {
        Search.getInstance().gotoHomepage();
    }
}
