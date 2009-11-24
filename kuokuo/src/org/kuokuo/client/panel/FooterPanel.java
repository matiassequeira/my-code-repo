/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import org.kuokuo.client.ServiceFactory;
import org.kuokuo.client.data.IndexStatus;
import org.kuokuo.client.service.SearchServiceAsync;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * footer panel
 * 
 * @version Nov 15, 2009 1:56:57 AM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class FooterPanel extends VerticalPanel
{
    protected HTML status;

    public FooterPanel()
    {
        this.setHorizontalAlignment(ALIGN_CENTER);
        DOM.setElementAttribute(this.getElement(), "id", "page-footer");
        HorizontalPanel firstRow = new HorizontalPanel();
        firstRow.setSpacing(5);
        status = new HTML();
        firstRow.add(status);
        refresh();
        this.add(firstRow);

        HorizontalPanel secondRow = new HorizontalPanel();
        secondRow.setSpacing(5);
        secondRow.add(new HTML("Kuokuo 1.2"));
        secondRow.add(new HTML("<a href='mailto:xuedm79@gmail.com'>报告错误</a>"));
        secondRow.add(new HTML("Power by GWT, Lucene & MMSeg4J"));
        this.add(secondRow);
    }

    public void refresh()
    {
        SearchServiceAsync searchService = ServiceFactory.SERVICE_SEARCH;
        
        searchService.getIndexStatus(new AsyncCallback<IndexStatus>()
        {

            public void onSuccess(IndexStatus result)
            {
                long docCount = result.getDocCount();
                long indexCost = result.getIndexCost();
                String lastUpdate = result.getLastUpdate();
                String str = "系统启动于" + result.getStartDate() + "，";
                str = str + "处理" + result.getQueryCount() + "条查询。";
                str = str + "最后一次更新：" + lastUpdate + "，索引" + docCount + "个对象，耗时" + (float) indexCost / 1000f + "秒";
                status.setText(str);
            }

            public void onFailure(Throwable caught)
            {

            }
        });
    }
}
