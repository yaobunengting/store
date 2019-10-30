package com.sgg.zh.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.sgg.zh.entity.Order;
import com.sgg.zh.entity.OrderItem;
import com.sgg.zh.utils.DataSourceUtils;

public class OrderDaoImpl implements com.sgg.zh.dao.OrderDao {

	/**
	 * 添加一条订单
	 * @throws SQLException 
	 */
	@Override
	public void add(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "INSERT INTO orders values(?, ?, ?, ?, ?, ?, ?, ?)";
		qr.update(DataSourceUtils.getConnection(), sql, order.getOid(), order.getOrdetTime(), order.getTotal(), order.getState(),
				order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid());
	}

	/**
	 * 添加一条订单项
	 * @throws SQLException 
	 */
	@Override
	public void addItem(OrderItem item) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "INSERT INTO orderItem value(?, ?, ?, ?, ?)";
		qr.update(DataSourceUtils.getConnection(), sql, item.getItemId(), item.getCount(), item.getSubtotal(), 
				item.getProduct().getPid(), item.getOrder().getOid()); 
	}

}
