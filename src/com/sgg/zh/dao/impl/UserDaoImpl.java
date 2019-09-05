package com.sgg.zh.dao.impl;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.taglibs.standard.tag.common.sql.DataSourceUtil;

import com.sgg.zh.Entity.User;
import com.sgg.zh.dao.UserDao;
import com.sgg.zh.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao{

	/**
	 * 添加用户
	 * @throws SQLException 
	 */
	@Override
	public void add(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//String sql = "INSERT INTO user VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
//		qr.update(sql, user.getUid(), user.getUsername(), user.getPassword(), 
//				user.getName(), user.getEmail(), user.getTelephpne(),
//				user.getBirthday(), user.getSex(), user.getState(), user.getCode());
		
		String sql = "INSERT INTO user VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
		qr.update(sql, user.getUid(), user.getUsername(), user.getPassword(), 
				user.getName(), user.getEmail(), user.getTelephpne(), user.getBirthday(),
				user.getSex(), user.getState(), user.getCode());
	}

	/**
	 * 用户激活
	 * 通过一个激活码获取一个用户
	 */
	@Override
	public User getByCode(String code) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM user WHERE code = ? LIMIT 1";
		return qr.query(sql, new BeanHandler<>(User.class), code);
	}

	/**
	 * 修改用户
	 */
	@Override
	public void update(User user) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "UPDATE user SET username = ?, password = ?, name = ?,"
				+ "email = ?, birthday = ?, state = ?, code = ? WHERE uid = ?";
		qr.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getBirthday(), user.getState(), null, user.getUid());
		
		
		
	}

	/**
	 * 用户登录
	 */
	@Override
	public User getByUsernameAndPwd(String username, String password) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM user WHERE username = ? AND password = ? LIMIT 1";
		return qr.query(sql, new BeanHandler<>(User.class), username, password);
	}

}
