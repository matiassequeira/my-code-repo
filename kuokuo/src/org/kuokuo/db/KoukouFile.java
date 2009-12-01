/**
 * 
 */
package org.kuokuo.db;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * @author Felix
 * 
 */
@Entity
@Table(name = "KUOKUO_FILE")
@Indexed
@Analyzer(impl = IKAnalyzer.class)
public class KoukouFile extends IdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String name;

	private String displayName;
	
	private String fullName;

	private Timestamp scanTime;

	private String extName;

	private String path;
	
	private String displayPath;

	private Boolean folderFlag;
	private Timestamp lastModifiedTime;
	private Timestamp creationTime;
	
	@Transient
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	@Transient
	public String getDisplayPath() {
		return displayPath;
	}
	public void setDisplayPath(String displayPath) {
		this.displayPath = displayPath;
	}

	private String tag;

	/**
	 * @return the name
	 */
	@Column(name = "NAME", nullable = false)
	@Field(index = Index.TOKENIZED, store = Store.YES)
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the fullName
	 */
	@Column(name = "FULL_NAME", nullable = false)
	@Field(index = Index.TOKENIZED, store = Store.YES)
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the scanTime
	 */
	@Column(name = "SCAN_TIME")
	public Timestamp getScanTime() {
		return scanTime;
	}

	/**
	 * @param scanTime
	 *            the scanTime to set
	 */
	public void setScanTime(Timestamp scanTime) {
		this.scanTime = scanTime;
	}

	/**
	 * @return the extName
	 */
	@Column(name = "EXT_NAME")
	public String getExtName() {
		return extName;
	}

	/**
	 * @param extName
	 *            the extName to set
	 */
	public void setExtName(String extName) {
		this.extName = extName;
	}

	/**
	 * @return the path
	 */
	@Column(name = "PATH")
	@Field(index = Index.TOKENIZED, store = Store.YES)
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the folderFlag
	 */
	@Column(name = "FOLDER_FLAG")
	public Boolean getFolderFlag() {
		return folderFlag;
	}

	/**
	 * @param folderFlag
	 *            the folderFlag to set
	 */
	public void setFolderFlag(Boolean folderFlag) {
		this.folderFlag = folderFlag;
	}

	/**
	 * @return the lastModifiedTime
	 */
	@Column(name = "LAST_MODIFIED_TIME")
	
	public Timestamp getLastModifiedTime() {
		return lastModifiedTime;
	}
	
	/**
	 * @param lastModifiedTime
	 *            the lastModifiedTime to set
	 */
	public void setLastModifiedTime(Timestamp lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	/**
	 * @return the creationTime
	 */
	@Column(name = "CREATION_TIME")
	public Timestamp getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime
	 *            the creationTime to set
	 */
	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the tag
	 */
	@Column(name = "TAG")
	@Field(index = Index.TOKENIZED, store = Store.YES)
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag
	 *            the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	public String toString() {
		return this.path+"/"+this.fullName;
	}
}
