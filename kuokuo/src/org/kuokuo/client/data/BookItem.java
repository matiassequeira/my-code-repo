/**
 * 
 */
package org.kuokuo.client.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @version Dec 15, 2009 9:33:41 PM
 * @author Dingmeng (xuedm79@gmail.com)
 *
 */
@Entity
@Table(name = "BOOK_ITEM")
public class BookItem extends KuokuoItem
{
    private static final long serialVersionUID = -3059015223854166623L;

    @Column(name = "ISBN", length = 20)
    private String ISBN;
    
    @Column(name = "AUTHOR", length = 100)
    private String author;
    
    @Column(name = "PUBLISHER", length = 100)
    private String publisher;
    
    /**
     * @return the iSBN
     */
    public String getISBN()
    {
        return ISBN;
    }

    /**
     * @param iSBN the iSBN to set
     */
    public void setISBN(String iSBN)
    {
        ISBN = iSBN;
    }

    /**
     * @return the author
     */
    public String getAuthor()
    {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author)
    {
        this.author = author;
    }

    /**
     * @return the publisher
     */
    public String getPublisher()
    {
        return publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }
}
