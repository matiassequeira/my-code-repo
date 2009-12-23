package org.kuokuo.client;

import org.kuokuo.client.data.QueryResult;
import org.kuokuo.client.panel.FooterPanel;
import org.kuokuo.client.panel.SearchResultPanel;
import org.kuokuo.client.panel.TitlePanel;
import org.kuokuo.client.panel.WelcomePanel;
import org.kuokuo.client.service.SearchService;
import org.kuokuo.client.service.SearchServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
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

    protected WelcomePanel welcomePanel;
    
    protected SearchResultPanel searchResultPanel;
    
    /**
     * This is the entry point method.
     */
    public void onModuleLoad()
    {
        instance = this;

        VerticalPanel panel = new VerticalPanel();
        panel.setWidth("100%");
        HorizontalPanel headerPanel = new HorizontalPanel();
        panel.add(headerPanel);
        panel.add(new HTML("<hr>"));

        titlePanel = new TitlePanel();
        panel.add(titlePanel);

        panel.add(new HTML("<hr>"));
        contentPanel = new VerticalPanel();
        contentPanel.setWidth("100%");
        
        contentPanel.clear();
        if(welcomePanel == null)
        {
            welcomePanel = new WelcomePanel();
        }
        contentPanel.add(welcomePanel);
        
        panel.add(contentPanel);
        panel.setCellHeight(contentPanel, "500px");
        panel.setCellWidth(contentPanel, "100%");
        panel.add(new HTML("<hr>"));

        footerPanel = new FooterPanel();
        panel.add(footerPanel);
        panel.setCellHorizontalAlignment(footerPanel, VerticalPanel.ALIGN_CENTER);

        RootPanel.get().add(panel);
    }

    public void doSearch(String query)
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
    
    public void gotoWelcome()
    {
        contentPanel.clear();
        if(welcomePanel == null)
        {
            welcomePanel = new WelcomePanel();
        }
        welcomePanel.refresh();
        contentPanel.add(welcomePanel);
    }
}
