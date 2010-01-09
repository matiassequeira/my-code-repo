/**
 * 
 */
package org.kuokuo.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

/**
 * @version Jan 9, 2010 1:32:52 PM
 * @author Dingmeng (xuedm79@gmail.com)
 *
 */
public class HistoryHandler implements ValueChangeHandler<String>
{
    public static final String HOME_PAGE = "home";
    
    public static final String QUERY = "query=";
    
    public void onValueChange(ValueChangeEvent<String> event)
    {
        String historyToken = event.getValue();
        
        if(historyToken == null || HOME_PAGE.equals(historyToken))
        {
            Search.getInstance().gotoHomepage(false);
        }
        else if(historyToken.startsWith(QUERY))
        {
            String query = historyToken.substring(QUERY.length());
            Search.getInstance().doSearch(query, false);
        }
        else
        {
            Search.getInstance().gotoHomepage(false);
        }
    }
}
