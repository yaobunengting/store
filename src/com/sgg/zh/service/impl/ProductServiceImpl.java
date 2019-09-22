package com.sgg.zh.service.impl;

import java.util.List;

import com.sgg.zh.dao.ProductDao;
import com.sgg.zh.dao.impl.ProductDaoImpl;
import com.sgg.zh.entity.Product;
import com.sgg.zh.service.ProductService;

public class ProductServiceImpl implements ProductService{

	/**
	 * ��ѯ������Ʒ
	 */
	@Override
	public List<Product> findNew() throws Exception {
		ProductDao pd = new ProductDaoImpl();
		return pd.findNew();
	}

	/**
	 * ��ѯ������Ʒ
	 */
	@Override
	public List<Product> findHot() throws Exception {
		ProductDao pd = new ProductDaoImpl();
		return pd.findHot();
	}

	/**
	 * ��ѯ������Ʒ
	 */
	@Override
	public Product getById(String pid) throws Exception {
		ProductDao pd = new ProductDaoImpl();
		return pd.getById(pid);
	}

}
