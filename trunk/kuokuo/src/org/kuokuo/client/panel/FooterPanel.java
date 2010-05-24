/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import org.kuokuo.client.ServiceFactory;
import org.kuokuo.client.data.IndexStatus;
import org.kuokuo.client.data.AppStatus;
import org.kuokuo.client.service.ContentServiceAsync;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
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
        this.setSpacing(5);
        this.setStylePrimaryName("footer");
        status = new HTML();
        status.setStyleName("page-footer");
        refresh();
        this.add(status);

        HTML html = new HTML("Kuokuo 1.5.1 <a href='mailto:xuedm79@gmail.com'>报告错误</a> Powered by GWT, Lucene, Hibernate & MMSeg4J");
        html.setStyleName("page-footer");
        this.add(html);
        
        Timer timer = new Timer()
        {
            public void run()
            {
                refresh();
            }
        };
        timer.scheduleRepeating(60000);
    }

    public void refresh()
    {
        ContentServiceAsync service = ServiceFactory.SERVICE_CONTENT;
        service.getAppStatus(new AsyncCallback<AppStatus>()
        {
            public void onSuccess(AppStatus result)
            {
                IndexStatus indexStatus = result.getIndexStatus();
                long docCount = indexStatus.getDocCount();
                long indexCost = indexStatus.getIndexCost();
                String lastUpdate = indexStatus.getLastUpdate();
                
                String str = "自2009年1月11日 有" + result.getVisitorCount() + "人访问本系统，";
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
