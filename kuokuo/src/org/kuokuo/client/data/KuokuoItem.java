/**
 * 
 */
package org.kuokuo.client.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.chenlb.mmseg4j.analysis.MaxWordAnalyzer;

/**
 * @version Dec 6, 2009 11:10:28 AM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
@Entity
@Table(name = "KUOKUO_ITEM")
@Indexed
@Analyzer(impl = MaxWordAnalyzer.class)
@Inheritance(strategy = InheritanceType.JOINED)
public class KuokuoItem extends PersistentObject
{
    private static final long serialVersionUID = 1485781894877463936L;

    public static final String TYPE_MOVIE = "movie";

    public static final String TYPE_GAME = "game";

    public static final String TYPE_MUSIC = "music";

    public static final String TYPE_BOOK = "book";

    public static final String TYPE_OTHER = "other";

    @Column(name = "TYPE", nullable = false, length = 50)
    private String type;

    @Column(name = "PATH", nullable = false, length = 1000)
    private String path;
    
    @Column(name = "FOLDER_PATH", nullable = false, length = 1000)
    private String folderPath;

    @Column(name = "NAME", nullable = false, length = 256)
    @Field(index = Index.TOKENIZED, store = Store.YES)
    private String name;

    @Column(name = "IS_FOLDER", nullable = false)
    private boolean isFolder;

    @Column(name = "FORMAT", length = 50)
    private String format;

    @Column(name = "LENGTH")
    private long length;

    @Column(name = "ROOT_PATH", length = 50)
    private String rootPath;

    @Column(name = "IMMORTAL_ID", length = 36)
    private String immortalId;

    @Column(name = "LAST_MODIFIED")
    private Date lastModified;
    
    @Transient
    private String highlightName;
    
    @Transient
    private float score;

    /**
     * @return the type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * @return the path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * @return the folderPath
     */
    public String getFolderPath()
    {
        return folderPath;
    }

    /**
     * @param folderPath the folderPath to set
     */
    public void setFolderPath(String folderPath)
    {
        this.folderPath = folderPath;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the isFolder
     */
    public boolean isFolder()
    {
        return isFolder;
    }

    /**
     * @param isFolder
     *            the isFolder to set
     */
    public void setFolder(boolean isFolder)
    {
        this.isFolder = isFolder;
    }

    /**
     * @return the format
     */
    public String getFormat()
    {
        return format;
    }

    /**
     * @param format
     *            the format to set
     */
    public void setFormat(String format)
    {
        this.format = format;
    }

    /**
     * @return the length
     */
    public long getLength()
    {
        return length;
    }

    /**
     * @param length
     *            the length to set
     */
    public void setLength(long length)
    {
        this.length = length;
    }

    /**
     * @return the rootPath
     */
    public String getRootPath()
    {
        return rootPath;
    }

    /**
     * @param rootPath
     *            the rootPath to set
     */
    public void setRootPath(String rootPath)
    {
        this.rootPath = rootPath;
    }

    /**
     * @return the immortalId
     */
    public String getImmortalId()
    {
        return immortalId;
    }

    /**
     * @param immortalId
     *            the immortalId to set
     */
    public void setImmortalId(String immortalId)
    {
        this.immortalId = immortalId;
    }

    /**
     * @return the lastModified
     */
    public Date getLastModified()
    {
        return lastModified;
    }

    /**
     * @param lastModified the lastModified to set
     */
    public void setLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
    }

    /**
     * @return the highlightName
     */
    public String getHighlightName()
    {
        return highlightName;
    }

    /**
     * @param highlightName the highlightName to set
     */
    public void setHighlightName(String highlightName)
    {
        this.highlightName = highlightName;
    }

    /**
     * @return the score
     */
    public float getScore()
    {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(float score)
    {
        this.score = score;
    }
}
