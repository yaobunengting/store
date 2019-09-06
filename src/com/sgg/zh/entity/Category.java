package com.sgg.zh.entity;

import java.io.Serializable;

/**
 * 分类实体
 * 
 * @author Administrator
 *
 */
public class Category implements Serializable{
	String cid;
	String cname;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Override
	public String toString() {
		return "Category [cid=" + cid + ", cname=" + cname + "]";
	}

}
