package org.kuokuo.client.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DOUBAN_RESOURCE")
public class DoubanResource extends PersistentObject
{
    private static final long serialVersionUID = 8903939680575778710L;

    @Column(name = "TYPE", nullable = false, length = 50)
    private String type;

    @Column(name = "DOUBAN_ID", nullable = false, length = 50)
    private String doubanId;
    
    @Column(name = "AVERAGE")
    private float average;
    
    @Column(name = "NUMRATERS")
    private int numRaters;

    @Column(name = "IMAGE", length = 100)
    private String image;
    
    @Column(name = "TAGS", length = 100)
    private String tags;
    
    @Column(name = "AUTHORS", length = 100)
    private String authors;

    @Column(name = "ALTERNATE", length = 100)
    private String alternate;

    @Column(name = "TITLE", length = 100)
    private String title;

    @Column(name = "SUMMARY", length = 1000)
    private String summary;

    @Column(name = "QUERY_WORDS", nullable = false, length = 256)
    private String queryWords;
    
    /**
     * @return the average
     */
    public float getAverage()
    {
        return average;
    }

    /**
     * @param average the average to set
     */
    public void setAverage(float average)
    {
        this.average = average;
    }

    /**
     * @return the numRaters
     */
    public int getNumRaters()
    {
        return numRaters;
    }

    /**
     * @param numRaters the numRaters to set
     */
    public void setNumRaters(int numRaters)
    {
        this.numRaters = numRaters;
    }

    /**
     * @return the image
     */
    public String getImage()
    {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image)
    {
        this.image = image;
    }

    /**
     * @return the tags
     */
    public String getTags()
    {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(String tags)
    {
        this.tags = tags;
    }

    /**
     * @return the authors
     */
    public String getAuthors()
    {
        return authors;
    }

    /**
     * @param authors the authors to set
     */
    public void setAuthors(String authors)
    {
        this.authors = authors;
    }

    /**
     * @return the alternate
     */
    public String getAlternate()
    {
        return alternate;
    }

    /**
     * @param alternate the alternate to set
     */
    public void setAlternate(String alternate)
    {
        this.alternate = alternate;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the queryWords
     */
    public String getQueryWords()
    {
        return queryWords;
    }

    /**
     * @param queryWords the queryWords to set
     */
    public void setQueryWords(String queryWords)
    {
        this.queryWords = queryWords;
    }

    /**
     * @return the type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * @return the doubanId
     */
    public String getDoubanId()
    {
        return doubanId;
    }

    /**
     * @param doubanId the doubanId to set
     */
    public void setDoubanId(String doubanId)
    {
        this.doubanId = doubanId;
    }

    /**
     * @return the summary
     */
    public String getSummary()
    {
        return summary;
    }

    /**
     * @param summary the summary to set
     */
    public void setSummary(String summary)
    {
        this.summary = summary;
    }
}
