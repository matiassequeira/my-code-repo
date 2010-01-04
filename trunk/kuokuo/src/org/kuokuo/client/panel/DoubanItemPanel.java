/**
 * 
 */
package org.kuokuo.client.panel;

import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.KuokuoItem;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @version Jan 4, 2010 10:45:45 PM
 * @author Dingmeng (xuedm79@gmail.com)
 *
 */
public abstract class DoubanItemPanel extends SearchResultItemPanel
{

    /**
     * @param item
     */
    public DoubanItemPanel(KuokuoItem item)
    {
        super(item);
    }

    @Override
    protected void buildContent(KuokuoItem item)
    {
        super.buildContent(item);

        DoubanResource resource = item.getDoubanResource();
        if (resource != null && resource.getId() != null)
        {
            thumbnail.setWidget(new HTML("<a href=\"" + resource.getAlternate() + "\" target=\"blank\"><img width=\"60px\" border=0 src=\""
                    + resource.getImage() + "\"></a>"));
            ratingPanel.setVisible(true);
            ratingPanel.setRating(resource.getAverage(), resource.getNumRaters());
        }
        else
        {
            ratingPanel.setVisible(false);
        }
    }

    @Override
    protected Panel[] buildExtPanels(KuokuoItem item)
    {
        DoubanResource resource = item.getDoubanResource();
        if (resource == null || resource.getId() == null)
        {
            return super.buildExtPanels(item);
        }
        
        Panel[] panels = new Panel[1];
        {
            SimplePanel panel = new SimplePanel();
            HTML html = new HTML(resource.getSummary());
            html.setStyleName("item-gray");
            panel.setWidget(html);
            panels[0] = panel;
        }
        return panels;
    }
}
