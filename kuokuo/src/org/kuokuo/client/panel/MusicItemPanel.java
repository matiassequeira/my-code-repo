/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import org.kuokuo.client.ServiceFactory;
import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.DoubanResourceType;
import org.kuokuo.client.data.QueryResultItem;
import org.kuokuo.client.service.SearchServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * movie content build
 * 
 * @version Nov 23, 2009 11:05:12 PM
 */
public class MusicItemPanel extends SearchResultItemPanel
{
    private SimplePanel doubanImage;

    /**
     * @param item
     */
    public MusicItemPanel(QueryResultItem item)
    {
        super(item);
    }

    /**
     * @see org.kuokuo.client.panel.SearchResultItemPanel#buildContent(org.kuokuo.client.data.QueryResultItem)
     */
    @Override
    protected void buildContent(QueryResultItem item)
    {
        HorizontalPanel panel = new HorizontalPanel();
        initWidget(panel);
        panel.setSpacing(5);
        // panel.add(new
        // HTML("<a href=\""+item.doubanURL+"\"><img border=0 src=\""+item.imageURL+"\"></a>"));
        doubanImage = new SimplePanel();
        doubanImage.setWidget(new HTML("<a href=\"#\"><img width=\"60px\" border=0 src=\"http://t.douban.com/pics/music-default-small.gif\"></a>"));
        panel.add(doubanImage);

        VerticalPanel vBox = new VerticalPanel();
        panel.add(vBox);

        {
            HorizontalPanel row = new HorizontalPanel();
            // firstRow.add(new HTML("<img width=\"16\" src=\"folder.gif\">"));
            row.add(new HTML("<b>" + item.getHighlightName() + "</b>"));
            row.add(new HTML("&nbsp;"));
            // row.add(new
            // HTML("<a href=\"http://www.douban.com/subject/"+item.doubanID+"/\">douban</a>"));
            // firstRow.add(new HTML("- <i>" + item.getScore() + "</i>"));
            vBox.add(row);
        }

        {
            vBox.add(new Label(item.getLastModified()));
        }

        {
            HorizontalPanel row = new HorizontalPanel();
            row.add(new HTML("<a href=\"" + item.getPath() + "\" target=\"blank\">" + trimName(item.getPath()) + "</a>"));
            row.add(new HTML("&nbsp;"));
            vBox.add(row);
        }

        // ajax load douban info
        SearchServiceAsync searchService = ServiceFactory.SERVICE_SEARCH;
        searchService.getDoubanInfo(item.getName(),DoubanResourceType.MUSIC, item.getPath(), new AsyncCallback<DoubanResource>()
        {

            public void onSuccess(DoubanResource result)
            {
                if(result != null)
                {
                    doubanImage.setWidget(new HTML("<a href=\"" + result.selfURL + "\"><img width=\"60px\" border=0 src=\"" + result.imageURL + "\"></a>"));
                }
            }

            public void onFailure(Throwable caught)
            {
                GWT.log("fail to load douban info", caught);
            }
        });
    }

}
