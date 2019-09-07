package com.sgg.zh.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.sgg.zh.dao.ProductDao;
import com.sgg.zh.entity.Product;
import com.sgg.zh.utils.DataSourceUtils;

public class ProductDaoImpl implements ProductDao {

	/**
	 * 查询最新商品
	 */
	@Override
	public List<Product> findNew() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM product ORDER BY pdate LIMIT 9";
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}

	/**
	 * 查询热门商品
	 */
	@Override
	public List<Product> findHot() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM product WHERE is_hot = 1 ORDER BY pdate LIMIT 9";
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}

}
