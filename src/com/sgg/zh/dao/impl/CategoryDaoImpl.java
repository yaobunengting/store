package com.sgg.zh.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.sgg.zh.dao.CategoryDao;
import com.sgg.zh.entity.Category;
import com.sgg.zh.utils.DataSourceUtils;

public class CategoryDaoImpl implements CategoryDao {
	private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());//Ĭ���ǿ�������

	/**
	 * ��ѯ���еķ���
	 */
	@Override
	public List<Category> findAll() throws Exception {
		String sql = "SELECT * FROM category";
		return qr.query(sql, new BeanListHandler<>(Category.class));
	}

	/**
	 * ��ӷ���
	 */
	@Override
	public void add(Category category) throws Exception {
		String sql = "INSERT INTO category VALUES(?, ?)";
		qr.update(sql, category.getCid(), category.getCname());
	}

	/**
	 * ͨ��id��ȡһ���������
	 */
	@Override
	public Category getById(String cid) throws Exception {
		
		String sql = "SELECT * FROM category WHERE cid = ? LIMIT 1";
		return qr.query(sql, new BeanHandler<>(Category.class), cid);
	}

	/**
	 * ���·�����Ϣ
	 */
	@Override
	public void update(Category c) throws Exception {
		String sql = "UPDATE category SET cname = ? WHERE cid = ?";
		qr.update(sql, c.getCname(), c.getCid());
	}

	/**
	 * ɾ������
	 */
	@Override
	public void delete(String cid) throws Exception {
		QueryRunner qr = new QueryRunner();//ȥ���Զ�����
		String sql = "DELETE FROM Category WHERE cid = ?";
		qr.update(DataSourceUtils.getConnection(), sql, cid);
	}

}
