/**
 * 
 */
package org.kuokuo.client.panel;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

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
    
    protected ListBox lstPageSize;
    
    public PaginationPanel()
    {
        this.setSpacing(5);
        lstPageSize = new ListBox(false);
        lstPageSize.addItem("5");
        lstPageSize.addItem("10");
        lstPageSize.addItem("20");
        lstPageSize.addItem("50");
        lstPageSize.setItemSelected(1, true);
        lstPageSize.setStyleName("paging-text");
        lstPageSize.addChangeHandler(new ChangeHandler()
        {
            public void onChange(ChangeEvent event)
            {
                int sel = lstPageSize.getSelectedIndex();
                pageSize = Integer.parseInt(lstPageSize.getItemText(sel));
                current = (int) Math.ceil(current / pageSize) * pageSize;
                page(current, pageSize);
            }
        });
        this.bindData(0, 0);
    }
    
    public void bindData(int current, int total)
    {
        this.clear();
        this.current = current;
        this.total = total;

        int totalPage = (total > 0) ? (int) (Math.ceil(total / pageSize)) : 0;        
        int currentPage = (int) (Math.ceil(current / pageSize));        

        if(currentPage > 0)
        {
            Anchor lnkFirst = new PagingAnchor("<<");
            lnkFirst.addClickHandler(new PageClickHandler(0, this));
            this.add(lnkFirst);
        }
        else
        {
            Label lnkFirst = new PagingLabel("<<");
            this.add(lnkFirst);
        }

        if(currentPage >= 10)
        {
            Anchor lnkPrevious = new PagingAnchor("<");
            //if current page is 15, clicking on the < link goes to the previous 1-10 pages, stop by the 1th page
            lnkPrevious.addClickHandler(new PageClickHandler((currentPage - 10 - currentPage%10 + 1) * pageSize, this));
            this.add(lnkPrevious);
        }
        else
        {
            Label lnkPrevious = new PagingLabel("<");
            this.add(lnkPrevious);
        }

        int basePage = (int) (Math.ceil(currentPage / 10)) * 10;
        for (int i = basePage; i < basePage + 10; i++)
        {
            if(i != currentPage && i <= totalPage)
            {
                Anchor link = new PagingAnchor(Integer.toString(i + 1));
                link.addClickHandler(new PageClickHandler(i * pageSize, this));
                this.add(link);
            }
            else
            {
                Label link = new PagingLabel(Integer.toString(i + 1));
                if(i == currentPage)
                {
                    link.setStyleName("paging-current");
                }
                this.add(link);
            }
        }
        
        if(currentPage + 10 < totalPage)
        {
            Anchor lnkNext = new PagingAnchor(">");
            //if current page is 5, clicking on the > link goes to the next 11-20 pages, stop by the 11th page
            lnkNext.addClickHandler(new PageClickHandler((currentPage + 10 - currentPage%10 + 1) * pageSize, this));
            this.add(lnkNext);
        }
        else
        {
            Label lnkNext = new PagingLabel(">");
            this.add(lnkNext);
        }

        if(currentPage < totalPage)
        {
            Anchor lnkLast = new PagingAnchor(">>");
            lnkLast.addClickHandler(new PageClickHandler(totalPage * pageSize, this));
            this.add(lnkLast);
        }
        else
        {
            Label lnkLast = new PagingLabel(">>");
            this.add(lnkLast);
        }
        
        HTML html = new HTML("　　共"+total+"条记录　　每页");
        html.setStyleName("paging-text");
        this.add(html);
        this.add(lstPageSize);
        html = new HTML("条");
        html.setStyleName("paging-text");
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
            this.setStyleName("paging-enable");
        }
    }
    
    private class PagingLabel extends Label
    {
        public PagingLabel(String text)
        {
            this.setText(text);
            this.setStyleName("paging-disable");
        }
    }
}
