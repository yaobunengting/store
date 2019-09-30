package com.sgg.zh.service.impl;

import java.util.List;

import com.sgg.zh.dao.CategoryDao;
import com.sgg.zh.dao.impl.CategoryDaoImpl;
import com.sgg.zh.entity.Category;
import com.sgg.zh.service.CategoryService;
import com.sgg.zh.utils.BeanFactory;

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

}
