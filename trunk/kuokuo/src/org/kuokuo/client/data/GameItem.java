/**
 * 
 */
package org.kuokuo.client.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @version Dec 12, 2009 11:42:33 PM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
@Entity
@Table(name = "GAME_ITEM")
public class GameItem extends PersistentObject
{
    private static final long serialVersionUID = 2283402638841867588L;

    @Column(name = "SUBTYPE", length = 10)
    private String subType;

    @Column(name = "CATEGORY", length = 10)
    private String category;

    @Column(name = "HAS_BOXSHOT")
    private boolean hasBoxshot;

    @Column(name = "SNAPSHOT_COUNT")
    private int snapshotCount;

    /**
     * @return the subType
     */
    public String getSubType()
    {
        return subType;
    }

    /**
     * @param subType
     *            the subType to set
     */
    public void setSubType(String subType)
    {
        this.subType = subType;
    }

    /**
     * @return the category
     */
    public String getCategory()
    {
        return category;
    }

    /**
     * @param category
     *            the category to set
     */
    public void setCategory(String category)
    {
        this.category = category;
    }

    /**
     * @return the hasBoxshot
     */
    public boolean isHasBoxshot()
    {
        return hasBoxshot;
    }

    /**
     * @param hasBoxshot
     *            the hasBoxshot to set
     */
    public void setHasBoxshot(boolean hasBoxshot)
    {
        this.hasBoxshot = hasBoxshot;
    }

    /**
     * @return the snapshotCount
     */
    public int getSnapshotCount()
    {
        return snapshotCount;
    }

    /**
     * @param snapshotCount
     *            the snapshotCount to set
     */
    public void setSnapshotCount(int snapshotCount)
    {
        this.snapshotCount = snapshotCount;
    }
}
