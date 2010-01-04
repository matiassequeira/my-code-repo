/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import org.kuokuo.client.data.KuokuoItem;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * movie content build
 * 
 * @version Nov 23, 2009 11:05:12 PM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class MovieItemPanel extends DoubanItemPanel
{
    /**
     * @param item
     */
    public MovieItemPanel(KuokuoItem item)
    {
        super(item);
    }

    @Override
    protected SimplePanel buildThumbnail()
    {
        SimplePanel ret = new SimplePanel();
        ret.setWidget(new HTML("<a href=\"#\"><img width=\"60px\" border=0 src=\"images/movie-default-small.gif\"></a>"));
        return ret;
    }
}
