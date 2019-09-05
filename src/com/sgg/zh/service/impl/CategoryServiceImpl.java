package com.sgg.zh.service.impl;

import java.util.List;

import com.sgg.zh.Entity.Category;
import com.sgg.zh.dao.CategoryDao;
import com.sgg.zh.dao.impl.CategoryDaoImpl;
import com.sgg.zh.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	/**
	 * 查询所有的分类
	 */
	@Override
	public List<Category> findAll() throws Exception {
		CategoryDao cd = new CategoryDaoImpl();
		return cd.findAll();
	}

}
