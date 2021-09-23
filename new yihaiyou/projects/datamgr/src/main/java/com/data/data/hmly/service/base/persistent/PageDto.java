package com.data.data.hmly.service.base.persistent;


public class PageDto {
	public static final String PAGE_MAP_KEY = "pageDto";
	public static final String PAGE_KEY = "page";
	public static final String PAGE_SIZE_KEY = "pageSize";
	private int page = 1;
	private int pageSize = 10;
	private int pageCount = 0;
	private int totalCount = 0;
	private int rowOffset = 0;
	private int rowLimit = 0;


	public PageDto(int sPage, int sPageSize) {

		if (sPageSize < 1) {
			sPageSize = Integer.MAX_VALUE;
		}
		this.page = sPage;
		this.pageSize = sPageSize;
	}

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return this.pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		refresh();
	}

	public int getRowOffset() {
		// 兼容原有写法
		if (totalCount == 0)
		{
			int rowPage = page - 1;
			if (rowPage < 0)
			{
				rowPage = 0;
			}
			
			return rowPage * pageSize;
		}
		return this.rowOffset;
	}

	public void setRowOffset(int rowOffset) {
		this.rowOffset = rowOffset;
	}

	public int getRowLimit() {
		// 兼容原有写法
		if (totalCount == 0)
		{
			return pageSize;
		}
		
		return this.rowLimit;
	}

	public void setRowLimit(int rowLimit) {
		this.rowLimit = rowLimit;
	}

	private void refresh() {
		int curTotalCount = getTotalCount();
		int curPage = getPage();
		int curPageSize = getPageSize();
		int curPageCount = (curTotalCount + curPageSize - 1) / curPageSize;
		int curRowOffset = 0;
		int curRowLimit = curPageSize;
		if (curPage > curPageCount) {
			curPage = curPageCount;
		}
		if (curTotalCount == 0) {
			curRowLimit = curTotalCount;
		}
		if (curPage == curPageCount) {
			curRowLimit = curTotalCount - curPageSize * (curPage - 1);
		}
		if (curPage <= 0) {
			curPage = 1;
		}
		curRowOffset = (curPage - 1) * curPageSize;
		setPage(curPage);
		setPageCount(curPageCount);
		setRowOffset(curRowOffset);
		setRowLimit(curRowLimit);
	}
}