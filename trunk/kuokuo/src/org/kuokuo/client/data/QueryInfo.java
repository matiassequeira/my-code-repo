/**
 * 
 */
package org.kuokuo.client.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @version Jan 10, 2010 12:23:15 AM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
@Entity
@Table(name = "QUERY_INFO")
public class QueryInfo extends PersistentObject
{

    private static final long serialVersionUID = 1591719531339106944L;

    private Date queryDate;

    private String queryWords;

    @OneToOne
    @JoinColumn(name = "VISITOR_FK")
    private VisitorInfo visitor;

    /**
     * @return the queryDate
     */
    public Date getQueryDate()
    {
        return queryDate;
    }

    /**
     * @param queryDate
     *            the queryDate to set
     */
    public void setQueryDate(Date queryDate)
    {
        this.queryDate = queryDate;
    }

    /**
     * @return the queryWords
     */
    public String getQueryWords()
    {
        return queryWords;
    }

    /**
     * @param queryWords
     *            the queryWords to set
     */
    public void setQueryWords(String queryWords)
    {
        this.queryWords = queryWords;
    }

    /**
     * @return the visitor
     */
    public VisitorInfo getVisitor()
    {
        return visitor;
    }

    /**
     * @param visitor
     *            the visitor to set
     */
    public void setVisitor(VisitorInfo visitor)
    {
        this.visitor = visitor;
    }
}
