/**
 * 
 */
package org.kuokuo.client.panel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * panel for pagination
 * 
 * @version Dec 8, 2009 10:06:16 PM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
public abstract class PaginationPanel extends HorizontalPanel
{
    public abstract void page(int start, int pageSize);

    protected void invokePage(int start)
    {
        page(start, pageSize);
    }
    
    protected int current;
    
    protected int pageSize = 10;
    
    protected int total;
    
    public PaginationPanel()
    {
        this.setSpacing(5);
    }
    
    public void bindData(int current, int total)
    {
        this.clear();
        this.current = current;
        this.total = total;

        int totalPage = (total > 0) ? (int) (Math.ceil(total / (double)pageSize)) : 0;        
        int currentPage = (int) (Math.ceil(current / pageSize));        

        if(currentPage > 0)
        {
            Anchor lnkPrevious = new PagingAnchor("上一页<<");
            //if current page is 15, clicking on the < link goes to the previous 1-10 pages, stop by the 1th page
            lnkPrevious.addClickHandler(new PageClickHandler((currentPage - 1) * pageSize, this));
            this.add(lnkPrevious);
        }
        else
        {
            Label lnkPrevious = new Label("　　　　");
            this.add(lnkPrevious);
        }

        int basePage = (currentPage > 4) ? currentPage - 4 : 0;
        for (int i = basePage; i < basePage + 11; i++)
        {
            if(i != currentPage && i <= totalPage - 1)
            {
                Anchor link = new PagingAnchor(Integer.toString(i + 1));
                link.addClickHandler(new PageClickHandler(i * pageSize, this));
                link.setStylePrimaryName("pageLink");
                this.add(link);
            }
            else if(i == currentPage)
            {
                Label link = new Label(Integer.toString(i + 1));
                this.add(link);
            }
        }
        
        if(currentPage <= totalPage - 2)
        {
            Anchor lnkNext = new PagingAnchor(">>下一页");
            lnkNext.addClickHandler(new PageClickHandler((currentPage + 1) * pageSize, this));
            this.add(lnkNext);
        }
        else
        {
            Label lnkNext = new Label("　　　　");
            this.add(lnkNext);
        }

        HTML html = new HTML("　　共"+total+"条记录");
        this.add(html);
    }
    
    private class PageClickHandler implements ClickHandler
    {
        private int start;
        
        private PaginationPanel hostPanel;
        
        public PageClickHandler(int start, PaginationPanel hostPanel)
        {
            this.start = start;
            this.hostPanel = hostPanel;
        }
        
        public void onClick(ClickEvent event)
        {
            this.hostPanel.invokePage(this.start);
        }
    }
    
    private class PagingAnchor extends Anchor
    {
        public PagingAnchor(String text)
        {
            this.setText(text);
        }
    }
}
