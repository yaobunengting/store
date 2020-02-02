package com.sgg.zh.service.impl;

import java.util.List;

import com.sgg.zh.dao.CategoryDao;
import com.sgg.zh.dao.ProductDao;
import com.sgg.zh.dao.impl.CategoryDaoImpl;
import com.sgg.zh.entity.Category;
import com.sgg.zh.service.CategoryService;
import com.sgg.zh.utils.BeanFactory;
import com.sgg.zh.utils.DataSourceUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CategoryServiceImpl implements CategoryService {
	CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
	/**
	 * 查询所有的分类
	 */
	@Override
	public List<Category> findAll() throws Exception {
		// 1.创建缓存管理器
		CacheManager cm = CacheManager.create(CategoryDaoImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		System.out.println(cm);
		// 2.获取指定的缓存
		Cache cache = cm.getCache("categoryCache");

		// 3.通过缓存获取数据
		Element element = cache.get("clist");
		List<Category> list = null;
		// 4.判断数据
		if (element == null) {
			// element为空的话,我们要从数据库中获取
			list = cd.findAll();
			// 将list放入缓存
			cache.put(new Element("clist", list));
			System.out.println("缓存中没有数据,已去数据库中获取");
		} else {
			// 直接返回
			list = (List<Category>) element.getObjectValue();
			System.out.println("缓存中已有数据");
		}

		return list;
	}
	
	/**
	 * 添加分类
	 */
	@Override
	public void add(Category category) throws Exception {
		cd.add(category);
		
		//更新缓存
		//1.创建缓存管理器
		CacheManager cm =  CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		
		//2.获取指定的缓存
		Cache cache = cm.getCache("categoryCache");
		
		//3.清空缓存
		cache.remove("clist");
		
	}

	/**
	 * 通过一个id获取一个分类对象
	 */
	@Override
	public Category getById(String cid) throws Exception {
		return cd.getById(cid);
	}

	/**
	 * 更新分类信息
	 */
	@Override
	public void update(Category c) throws Exception {
		//调用dao更新
		cd.update(c);
		
		//清空缓存
		//创建缓存管理器
		CacheManager cm =  CacheManager.create(CategoryDaoImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		
		//获取指定的缓存
		Cache cache = cm.getCache("categoryCache");
		
		//清空缓存
		cache.remove("clist");
	}

	/**
	 * 删除分类
	 * @throws Exception 
	 */
	@Override
	public void delete(String cid) throws Exception {
		
		try {
			//开启事务
			DataSourceUtils.startTransaction();
			
			//更新商品
			ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
			pd.updateCid(cid);
			
			//删除分类
			cd.delete(cid);
			//事务控制
			DataSourceUtils.commitAndClose();
			
			//清空缓存
			CacheManager cm = CacheManager.create(CategoryDaoImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
			Cache cache = cm.getCache("categoryCache");
			cache.remove("clist");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
		
	}

}
