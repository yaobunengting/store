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
		return pd.findHot();
	}

	/**
	 * ��ѯ������Ʒ
	 */
	@Override
	public Product getById(String pid) throws Exception {
		return pd.getById(pid);
	}

	/**
	 * ������ҳ��ѯ��Ʒ
	 */
	@Override
	public PageBean<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
		//1.��ǰҳ����
		List<Product> list = pd.findByPage(currPage, pageSize, cid);
		
		//2.����
		int totalCount = pd.getTotalCount(cid);
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}

}
