package com.sgg.zh.service;

import java.util.List;

import com.sgg.zh.entity.Product;

public interface ProductService {

	List<Product> findNew() throws Exception;

	List<Product> findHot() throws Exception;

	Product getById(String pid) throws Exception;

}
