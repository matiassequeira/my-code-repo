/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import org.kuokuo.client.ServiceFactory;
import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.client.service.ContentServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * movie content build
 * 
 * @version Nov 23, 2009 11:05:12 PM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class MovieItemPanel extends SearchResultItemPanel
{
    private static ContentServiceAsync service = ServiceFactory.SERVICE_CONTENT;
    
    /**
     * @param item
     */
    public MovieItemPanel(KuokuoItem item)
    {
        super(item);
    }

    /**
     * @see org.kuokuo.client.panel.SearchResultItemPanel#buildContent(org.kuokuo.client.data.QueryResultItem)
     */
    @Override
    protected void buildContent(KuokuoItem item)
    {
        super.buildContent(item);

        service.getDoubanResource(KuokuoItem.TYPE_MOVIE, item.getName(), new AsyncCallback<DoubanResource>()
        {

            public void onFailure(Throwable caught)
            {
                GWT.log("fail to load douban info", caught);
            }

            public void onSuccess(DoubanResource result)
            {
                if(result != null && result.getImage() != null && thumbnail != null)
                {
                    thumbnail.setWidget(new HTML("<a href=\"" + result.getAlternate() + "\" target=\"blank\"><img width=\"60px\" border=0 src=\"" + result.getImage() + "\"></a>"));
                }
            }
        });
    }

    /* (non-Javadoc)
     * @see org.kuokuo.client.panel.SearchResultItemPanel#buildThumbnail()
     */
    @Override
    protected SimplePanel buildThumbnail()
    {
        SimplePanel ret = new SimplePanel();
        ret.setWidget(new HTML("<a href=\"#\"><img width=\"60px\" border=0 src=\"http://t.douban.com/pics/movie-default-small.gif\"></a>"));
        return ret;
    }
}
