/**
 * 
 */
package org.kuokuo.client.panel;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @version Dec 23, 2009 11:13:46 PM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
public class RatingPanel extends SimplePanel
{
    public void setRating(float rating, int votes)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("评分：");
        int fullStars = (int) (rating + 0.5) / 2;
        int halfStar = (rating + 0.5 - fullStars * 2 >= 1) ? 1 : 0;
        int grayStars = 5 - fullStars - halfStar;

        for (int i = 0; i < fullStars; i++)
        {
            sb.append("<img width=12px border=0 src=\"images/star_red.gif\">");
        }
        if (halfStar == 1)
        {
            sb.append("<img width=12px border=0 src=\"images/star_red2.gif\">");
        }
        for (int i = 0; i < grayStars; i++)
        {
            sb.append("<img width=12px border=0 src=\"images/star_gray.gif\">");
        }
        sb.append("&nbsp;");
        sb.append(NumberFormat.getFormat("#.#").format(rating));
        sb.append("&nbsp;/&nbsp;");
        sb.append(NumberFormat.getFormat("#,###").format(votes));
        sb.append("&nbsp;votes");
        this.setWidget(new HTML(sb.toString()));
    }
}
