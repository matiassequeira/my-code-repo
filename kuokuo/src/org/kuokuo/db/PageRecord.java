/**
 * 
 */
package org.kuokuo.db;

import java.util.List;

/**
 * @author Felix
 * 
 */
public class PageRecord {
	private List result;
	private int totalCount;
	private int pageNo;
	private int pageSize;

	public PageRecord() {

	}

	public PageRecord(List result, int totalCount, int pageNo, int pageSize) {
		this.result = result;
		this.totalCount = totalCount;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	/**
	 * @return the result
	 */
	public List getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(List result) {
		this.result = result;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
