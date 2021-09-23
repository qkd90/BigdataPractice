package com.zuipin.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.zuipin.entity.TCatSort;

/**
 * @版权：象屿商城 版权所有 (c) 2012
 * @author:zhengry
 * @version Revision 2.0.0
 * @email:zryuan@xiangyu.cn
 * @see:
 * @创建日期：2012-11-14
 * @功能说明：搜索分类基类
 * @param <T>
 */
public class PageVo<T> {
    protected int               pageNo          = 1;
    protected int               pageSize        = 20;
    protected boolean           autoCount       = true;
    protected List<T>           result          = Collections.emptyList();
    protected List<SearchCatVO> secSearchCatVOs = new ArrayList<SearchCatVO>();
    protected List<SearchCatVO> subSearchCatVOs = new ArrayList<SearchCatVO>();
    protected List<TCatSort>    catSorts        = Collections.emptyList();
    protected List<TSortValue>  sortValues      = Collections.emptyList();
    protected long              totalCount      = 0;
    protected List<SearchStoreVO> searchStoreVOs = new ArrayList<SearchStoreVO>();
    
    public PageVo() {
    }
    
    public PageVo(final int pageSize) {
        setPageSize(pageSize);
    }
    
    public PageVo(final int pageSize, final boolean autoCount) {
        setPageSize(pageSize);
        setAutoCount(autoCount);
    }
    
    public int getPageNo() {
        return pageNo;
    }
    
    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;
        if (pageNo < 1) {
            this.pageNo = 1;
        }
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
        if (pageSize < 1) {
            this.pageSize = 1;
        }
    }
    
    public int getFirst() {
        return ((pageNo - 1) * pageSize) + 1;
    }
    
    public boolean isAutoCount() {
        return autoCount;
    }
    
    public void setAutoCount(final boolean autoCount) {
        this.autoCount = autoCount;
    }
    
    public List<T> getResult() {
        return result;
    }
    
    public void setResult(final List<T> result) {
        this.result = result;
    }
    
    public List<SearchCatVO> getSecSearchCatVOs() {
        return secSearchCatVOs;
    }
    
    public void setSecSearchCatVOs(List<SearchCatVO> secSearchCatVOs) {
        this.secSearchCatVOs = secSearchCatVOs;
    }
    
    public List<SearchCatVO> getSubSearchCatVOs() {
        return subSearchCatVOs;
    }
    
    public void setSubSearchCatVOs(List<SearchCatVO> subSearchCatVOs) {
        this.subSearchCatVOs = subSearchCatVOs;
    }
    
    public List<TCatSort> getCatSorts() {
        return catSorts;
    }
    
    public void setCatSorts(List<TCatSort> catSorts) {
        this.catSorts = catSorts;
    }
    
    public List<TSortValue> getSortValues() {
        return sortValues;
    }
    
    public void setSortValues(List<TSortValue> sortValues) {
        this.sortValues = sortValues;
    }
    
    public long getTotalCount() {
        return totalCount;
    }
    
    public void setTotalCount(final long totalCount) {
        this.totalCount = totalCount;
    }
    
    public long getTotalPages() {
        if (totalCount < 0)
            return -1;
        long count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        return count;
    }
    
    public boolean isHasNext() {
        return (pageNo + 1 <= getTotalPages());
    }
    
    public int getNextPage() {
        if (isHasNext())
            return pageNo + 1;
        else
            return pageNo;
    }
    
    public boolean isHasPre() {
        return (pageNo - 1 >= 1);
    }
    
    public int getPrePage() {
        if (isHasPre())
            return pageNo - 1;
        else
            return pageNo;
    }

	public List<SearchStoreVO> getSearchStoreVOs() {
		return searchStoreVOs;
	}

	public void setSearchStoreVOs(List<SearchStoreVO> searchStoreVOs) {
		this.searchStoreVOs = searchStoreVOs;
	}
    
    
}
