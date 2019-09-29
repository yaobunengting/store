package com.sgg.zh.dao;

import java.util.List;

import com.sgg.zh.entity.Product;

public interface ProductDao {

	List<Product> findNew() throws Exception;

	List<Product> findHot() throws Exception;

	Product getById(String pid) throws Exception;

	List<Product> findByPage(int currPage, int pageSize, String cid) throws Exception;

	int getTotalCount(String cid) throws Exception;

}
