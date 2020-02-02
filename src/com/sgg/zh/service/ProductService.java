package com.sgg.zh.service;

import java.util.List;

import com.sgg.zh.entity.PageBean;
import com.sgg.zh.entity.Product;

public interface ProductService {

	List<Product> findNew() throws Exception;

	List<Product> findHot() throws Exception;

	Product getById(String pid) throws Exception;

	PageBean<Product> findByPage(int currPage, int pageSize, String cid) throws Exception;

	List<Product> findAll() throws Exception;

	void add(Product p) throws Exception;

	void update(Product p) throws Exception;

	List<Product> findAllByTakeOff() throws Exception;

}
