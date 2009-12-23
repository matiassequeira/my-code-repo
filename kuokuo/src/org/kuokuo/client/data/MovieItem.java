/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * movie query result item
 * 
 * @version Nov 23, 2009 11:16:30 PM
 * @author Dingmeng (xuedm79@gmail.com)
 */
@Entity
@Table(name = "MOVIE_ITEM")
public class MovieItem extends PersistentObject
{
    private static final long serialVersionUID = -2637730304977853757L;
    
    @Column(name = "IMDB", length = 10)
    private String imdb;

    @Column(name = "IS_HIGH_DEFINITION")
    private boolean isHighDefinition;
    
    @Column(name = "VERTICAL_PIXELS")
    private int verticalPixels;
    
    @Column(name = "IMMORTAL_ID", length = 36)
    private String immortalId;
    
    /**
     * @return the imdb
     */
    public String getImdb()
    {
        return imdb;
    }

    /**
     * @param imdb the imdb to set
     */
    public void setImdb(String imdb)
    {
        this.imdb = imdb;
    }

    /**
     * @return the isHighDefinition
     */
    public boolean isHighDefinition()
    {
        return isHighDefinition;
    }

    /**
     * @param isHighDefinition the isHighDefinition to set
     */
    public void setHighDefinition(boolean isHighDefinition)
    {
        this.isHighDefinition = isHighDefinition;
    }

    /**
     * @return the verticalPixels
     */
    public int getVerticalPixels()
    {
        return verticalPixels;
    }

    /**
     * @param verticalPixels the verticalPixels to set
     */
    public void setVerticalPixels(int verticalPixels)
    {
        this.verticalPixels = verticalPixels;
    }

    /**
     * @return the immortalId
     */
    public String getImmortalId()
    {
        return immortalId;
    }

    /**
     * @param immortalId the immortalId to set
     */
    public void setImmortalId(String immortalId)
    {
        this.immortalId = immortalId;
    }
}
