package com.sgg.zh.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

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

	/**
	 * 查询单个商品
	 */
	@Override
	public Product getById(String pid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM product WHERE pid = ?";
		return qr.query(sql, new BeanHandler<>(Product.class), pid);
	}

	@Override
	public List<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM product WHERE cid = ? LIMIT ?,?";
		
		return qr.query(sql, new BeanListHandler<>(Product.class), cid, (currPage-1)*pageSize, pageSize);
	}

	@Override
	public int getTotalCount(String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT count(*) FROM product WHERE cid = ?";
		return ((Long)qr.query(sql, new ScalarHandler(), cid)).intValue();
	}

}
