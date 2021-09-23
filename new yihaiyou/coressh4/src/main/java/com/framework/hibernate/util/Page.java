package com.framework.hibernate.util;

import java.util.List;

import javax.persistence.Transient;

public class Page {
	
	public static final int	DEFAULT_PAGE_SIZE			= 16;
	public static final int	DEFAULT_PAGE_INDEX_COUNT	= 10;
	private List<?>			data;
	private int				pageIndex;
	private int				pageSize					= DEFAULT_PAGE_SIZE;
	private int				totalCount;
	private int				pageCount;
	private int				pageIndexCount;
	private int[]			pageinfo;
	
	public int getPageIndexCount() {
		return pageIndexCount;
	}
	
	public void setPageIndexCount(int pageIndexCount) {
		this.pageIndexCount = pageIndexCount;
	}
	
	public Page() {
		new Page(1);
	}
	
	public Page(int pageIndex, int pageSize, int pageIndexCount) {
		// check:
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageSize < 1)
			pageSize = 1;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.pageIndexCount = pageIndexCount;
	}
	
	public Page(int pageIndex, int pageSize) {
		this(pageIndex, pageSize, DEFAULT_PAGE_INDEX_COUNT);
	}
	
	public Page(int pageIndex) {
		this(pageIndex, DEFAULT_PAGE_SIZE, DEFAULT_PAGE_INDEX_COUNT);
	}
	
	public int getPageIndex() {
		return pageIndex;
	}
	
	public int getPrePageIndex() {
		return pageIndex - 1 == 0 ? 1 : pageIndex - 1;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public int getFirstResult() {
		return (pageIndex - 1) * pageSize;
	}
	
	@Transient
	public boolean getHasPrevious() {
		return pageIndex > 1;
	}
	
	@Transient
	public boolean getHasNext() {
		return pageIndex < pageCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		pageCount = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
		// adjust pageIndex:
		if (totalCount == 0) {
			if (pageIndex != 1)
				throw new IndexOutOfBoundsException("Page index out of range.");
		} else {
			if (pageIndex > pageCount)
				throw new IndexOutOfBoundsException("Page index out of range.");
		}
	}
	
	@Transient
	public boolean isEmpty() {
		return totalCount == 0;
	}
	
	public String toString() {
		StringBuffer msg = new StringBuffer();
		msg.append("pageIndex: ");
		msg.append(pageIndex);
		msg.append(" pageSize: ");
		msg.append(pageSize);
		msg.append(" totalCount: ");
		msg.append(totalCount);
		msg.append(" pageCount: ");
		msg.append(pageCount);
		return msg.toString();
	}
	
	@Transient
	public int[] getPageinfo() {
		
		if (pageIndex < 5) {
			if (pageCount > 9) {
				pageinfo = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			} else {
				pageinfo = new int[pageCount];
				for (int i = 0; i < pageCount; i++) {
					
					pageinfo[i] = i + 1;
				}
			}
		} else if ((pageCount - pageIndex) < 5) {
			if (pageCount > 9) {
				pageinfo = new int[9];
				int count = pageCount;
				for (int i = 8; i > -1; i--) {
					pageinfo[i] = count--;
				}
			} else {
				pageinfo = new int[pageCount];
				int count = pageCount;
				for (int i = pageCount - 1; i > -1; i--) {
					pageinfo[i] = count--;
				}
			}
			
		} else {
			pageinfo = new int[9];
			int begin = pageIndex;
			for (int i = 0; i < pageinfo.length; i++) {
				pageinfo[i] = begin++;
			}
		}
		
		return pageinfo;
	}
	
	public void setPageinfo(int[] pageinfo) {
		this.pageinfo = pageinfo;
	}
	
	public static void main(String[] args) {
		Page page = new Page(8, 10);
		page.setTotalCount(91);
		for (int i = 0; i < page.getPageinfo().length; i++) {
			System.out.print(page.getPageinfo()[i] + " ");
		}
	}
	
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public List<?> getData() {
		return data;
	}
	
	public void setData(List<?> data) {
		this.data = data;
	}
	
}