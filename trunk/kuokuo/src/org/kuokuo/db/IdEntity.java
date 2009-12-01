package org.kuokuo.db;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.DocumentId;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略. 子类可重载getId()函数重定义id的列名映射和生成策略.
 * 
 * @author calvin
 */
// JPA 基类的标识
@MappedSuperclass
public abstract class IdEntity {

	
	private String id;

	private Timestamp recordCreateTime;
	private Timestamp recordUpdateTime;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@DocumentId
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the recordCreateTime
	 */
	@Column(name = "RECORD_CREATE_TIME")
	public Timestamp getRecordCreateTime() {
		return recordCreateTime;
	}

	/**
	 * @param recordCreateTime
	 *            the recordCreateTime to set
	 */
	public void setRecordCreateTime(Timestamp recordCreateTime) {
		this.recordCreateTime = recordCreateTime;
	}

	/**
	 * @return the recordUpdateTime
	 */
	@Column(name = "RECORD_UPDATE_TIME")
	public Timestamp getRecordUpdateTime() {
		return recordUpdateTime;
	}

	/**
	 * @param recordUpdateTime
	 *            the recordUpdateTime to set
	 */
	public void setRecordUpdateTime(Timestamp recordUpdateTime) {
		this.recordUpdateTime = recordUpdateTime;
	}

}
