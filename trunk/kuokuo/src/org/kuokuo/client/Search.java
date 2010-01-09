package org.kuokuo.client;

import org.kuokuo.client.data.QueryResult;
import org.kuokuo.client.panel.FooterPanel;
import org.kuokuo.client.panel.SearchResultPanel;
import org.kuokuo.client.panel.TitlePanel;
import org.kuokuo.client.panel.UpdatesPanel;
import org.kuokuo.client.service.SearchService;
import org.kuokuo.client.service.SearchServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Search implements EntryPoint
{
    /**
     * Create a remote service proxy to talk to the server-side Greeting
     * service.
     */
    private final SearchServiceAsync searchService = GWT.create(SearchService.class);

    private static Search instance;

    public static Search getInstance()
    {
        return instance;
    }

    protected FooterPanel footerPanel;

    protected TitlePanel titlePanel;

    protected VerticalPanel contentPanel;

    protected UpdatesPanel welcomePanel;
    
    protected SearchResultPanel searchResultPanel;
    
    /**
     * This is the entry point method.
     */
    public void onModuleLoad()
    {
        instance = this;

        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.setWidth("100%");

        VerticalPanel centerWrapper = new VerticalPanel();
        centerWrapper.setWidth("910px");
        centerWrapper.setStylePrimaryName("centerWrapper");

        titlePanel = new TitlePanel();
        titlePanel.setWidth("910px");
        titlePanel.setHeight("158px");
        centerWrapper.add(titlePanel);
        centerWrapper.setCellHorizontalAlignment(titlePanel, VerticalPanel.ALIGN_CENTER);

        HorizontalPanel contentWrapper = new HorizontalPanel();
        contentWrapper.setWidth("910px");
        contentWrapper.setHeight("500px");
        
        contentPanel = new VerticalPanel();
        contentPanel.setStylePrimaryName("primaryContent");
        contentPanel.setWidth("100%");
        contentWrapper.add(contentPanel);
        contentWrapper.setCellWidth(contentPanel, "585px");
        
        contentPanel.clear();
        if (welcomePanel == null)
        {
            welcomePanel = new UpdatesPanel();
        }
        contentPanel.add(welcomePanel);
        
        VerticalPanel secondaryContent = new VerticalPanel();
        secondaryContent.setWidth("100%");
        secondaryContent.setHeight("500px");
        secondaryContent.setStylePrimaryName("secondaryContent");
        contentWrapper.add(secondaryContent);
        contentWrapper.setCellWidth(secondaryContent, "267px");
        
        centerWrapper.add(contentWrapper);
        centerWrapper.setCellHorizontalAlignment(contentWrapper, VerticalPanel.ALIGN_CENTER);

        mainPanel.add(centerWrapper);
        mainPanel.setCellHorizontalAlignment(centerWrapper, VerticalPanel.ALIGN_CENTER);
        
        footerPanel = new FooterPanel();
        footerPanel.setWidth("100%");
        mainPanel.add(footerPanel);
        mainPanel.setCellHorizontalAlignment(footerPanel, VerticalPanel.ALIGN_CENTER);

        RootPanel.get().add(mainPanel);
        RootPanel.get().setStylePrimaryName("root");
        
        History.addValueChangeHandler(new HistoryHandler());
    }

    public void doSearch(String query)
    {
        History.newItem(HistoryHandler.QUERY + query);
    }
    
    void doSearch(final String query, final boolean history)
    {
        searchService.query(query, new AsyncCallback<QueryResult>()
        {
            public void onFailure(Throwable caught)
            {
                
            }

            public void onSuccess(QueryResult result)
            {
                contentPanel.clear();
                if(searchResultPanel == null)
                {
                    searchResultPanel = new SearchResultPanel();
                }
                searchResultPanel.bindData(result);
                contentPanel.add(searchResultPanel);
                footerPanel.refresh();
            }
        });
    }
    
    public void gotoHomepage()
    {
        History.newItem(HistoryHandler.HOME_PAGE);
    }

    void gotoHomepage(boolean history)
    {
        contentPanel.clear();
        if(welcomePanel == null)
        {
            welcomePanel = new UpdatesPanel();
        }
        contentPanel.add(welcomePanel);
        titlePanel.reset();
    }
}
