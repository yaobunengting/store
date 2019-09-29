package com.sgg.zh.entity;

import java.util.List;

/**
 * 分页
 * 
 * @author Administrator
 * @param <E>
 *
 */
public class PageBean<E> {
	private List<E> list; // 数据
	private Integer currPage; // 当前页
	private Integer pageSize; // 每一页多少数据
	private Integer totalPage; // 总页数
	private Integer totalCount; // 总数

	public PageBean() {
	}

	public PageBean(List<E> list, Integer currPage, Integer pageSize, Integer totalCount) {
		super();
		this.list = list;
		this.currPage = currPage;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public Integer getCurrPage() {
		return currPage;
	}

	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalPage() {
		return (int) Math.ceil(totalCount * 1.0 / pageSize);
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public String toString() {
		return "PageBean [list=" + list + ", currPage=" + currPage + ", pageSize=" + pageSize + ", totalPage="
				+ this.getTotalPage() + ", totalCount=" + totalCount + "]";
	}

	
}
