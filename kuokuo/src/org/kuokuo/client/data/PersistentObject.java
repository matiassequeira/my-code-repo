/**
 * 
 */
package org.kuokuo.client.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.search.annotations.DocumentId;

/**
 * @version Dec 6, 2009 11:01:57 AM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PersistentObject implements Serializable
{
    private static final long serialVersionUID = 5237032887863294183L;

    @Id
    @Column(name = "ID", unique = true, nullable = false, length=36)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid", parameters =
    { @Parameter(name = "separator", value = "-") })
    @DocumentId
    private String id;

    @Column(name = "CREATE_USER", nullable = false, length=50)
    private String createUser;

    @Column(name = "CREATE_DATETIME", nullable = false)
    private Date createDatetime;

    @Column(name = "MODIFY_USER", length=50)
    private String modifyUser;

    @Column(name = "MODIFY_DATETIME")
    private Date modifyDatetime;

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the createUser
     */
    public String getCreateUser()
    {
        return createUser;
    }

    /**
     * @param createUser
     *            the createUser to set
     */
    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    /**
     * @return the createDatetime
     */
    public Date getCreateDatetime()
    {
        return createDatetime;
    }

    /**
     * @param createDatetime
     *            the createDatetime to set
     */
    public void setCreateDatetime(Date createDatetime)
    {
        this.createDatetime = createDatetime;
    }

    /**
     * @return the modifyUser
     */
    public String getModifyUser()
    {
        return modifyUser;
    }

    /**
     * @param modifyUser
     *            the modifyUser to set
     */
    public void setModifyUser(String modifyUser)
    {
        this.modifyUser = modifyUser;
    }

    /**
     * @return the modifyDatetime
     */
    public Date getModifyDatetime()
    {
        return modifyDatetime;
    }

    /**
     * @param modifyDatetime
     *            the modifyDatetime to set
     */
    public void setModifyDatetime(Date modifyDatetime)
    {
        this.modifyDatetime = modifyDatetime;
    }
}
