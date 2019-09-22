package com.sgg.zh.service.impl;

import java.util.List;

import com.sgg.zh.dao.ProductDao;
import com.sgg.zh.dao.impl.ProductDaoImpl;
import com.sgg.zh.entity.Product;
import com.sgg.zh.service.ProductService;

public class ProductServiceImpl implements ProductService{

	/**
	 * 查询最新商品
	 */
	@Override
	public List<Product> findNew() throws Exception {
		ProductDao pd = new ProductDaoImpl();
		return pd.findNew();
	}

	/**
	 * 查询热门商品
	 */
	@Override
	public List<Product> findHot() throws Exception {
		ProductDao pd = new ProductDaoImpl();
		return pd.findHot();
	}

	/**
	 * 查询单个商品
	 */
	@Override
	public Product getById(String pid) throws Exception {
		ProductDao pd = new ProductDaoImpl();
		return pd.getById(pid);
	}

}
