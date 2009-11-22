/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.data;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * query result item
 * 
 * @version Nov 13, 2009 3:59:14 PM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class QueryResultItem implements IsSerializable
{
    private float score;

    private String path;

    private String name;

    private String highlightName;

    private String lastModified;

    private String suffix;

    private boolean isFolder;

    private long timestamp;
    
    /**
     * @return Returns the timestamp.
     */
    public long getTimestamp()
    {
        return timestamp;
    }

    /**
     * @param timestamp
     *            The timestamp to set.
     */
    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    /**
     * @return Returns the score.
     */
    public float getScore()
    {
        return score;
    }

    /**
     * @param score
     *            The score to set.
     */
    public void setScore(float score)
    {
        this.score = score;
    }

    /**
     * @return Returns the path.
     */
    public String getPath()
    {
        return path;
    }

    /**
     * @param path
     *            The path to set.
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return Returns the lastModified.
     */
    public String getLastModified()
    {
        return lastModified;
    }

    /**
     * @param lastModified
     *            The lastModified to set.
     */
    public void setLastModified(String lastModified)
    {
        this.lastModified = lastModified;
    }

    /**
     * @return Returns the highlightName.
     */
    public String getHighlightName()
    {
        return highlightName;
    }

    /**
     * @param highlightName
     *            The highlightName to set.
     */
    public void setHighlightName(String highlightName)
    {
        this.highlightName = highlightName;
    }

    /**
     * @return Returns the suffix.
     */
    public String getSuffix()
    {
        return suffix;
    }

    /**
     * @param suffix
     *            The suffix to set.
     */
    public void setSuffix(String suffix)
    {
        this.suffix = suffix;
    }

    /**
     * @return Returns the isFolder.
     */
    public boolean isFolder()
    {
        return isFolder;
    }

    /**
     * @param isFolder
     *            The isFolder to set.
     */
    public void setFolder(boolean isFolder)
    {
        this.isFolder = isFolder;
    }
}
