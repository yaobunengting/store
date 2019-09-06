package com.sgg.zh.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.sgg.zh.dao.CategoryDao;
import com.sgg.zh.entity.Category;
import com.sgg.zh.utils.DataSourceUtils;

public class CategoryDaoImpl implements CategoryDao {

	/**
	 * 查询所有的分类
	 */
	@Override
	public List<Category> findAll() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM category";
		return qr.query(sql, new BeanListHandler<>(Category.class));
	}

}
