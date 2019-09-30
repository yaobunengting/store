package com.sgg.zh.service.impl;

import java.util.List;

import com.sgg.zh.dao.ProductDao;
import com.sgg.zh.dao.impl.ProductDaoImpl;
import com.sgg.zh.entity.PageBean;
import com.sgg.zh.entity.Product;
import com.sgg.zh.service.ProductService;
import com.sgg.zh.utils.BeanFactory;

public class ProductServiceImpl implements ProductService{
	ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
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
		return pd.findHot();
	}

	/**
	 * 查询单个商品
	 */
	@Override
	public Product getById(String pid) throws Exception {
		return pd.getById(pid);
	}

	/**
	 * 按类别分页查询商品
	 */
	@Override
	public PageBean<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
		//1.当前页数据
		List<Product> list = pd.findByPage(currPage, pageSize, cid);
		
		//2.总数
		int totalCount = pd.getTotalCount(cid);
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}

}
