package com.sgg.zh.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.sgg.zh.entity.Order;
import com.sgg.zh.entity.OrderItem;
import com.sgg.zh.entity.Product;
import com.sgg.zh.utils.DataSourceUtils;

public class OrderDaoImpl implements com.sgg.zh.dao.OrderDao {

	/**
	 * ���һ������
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
	 * ���һ��������
	 * @throws SQLException 
	 */
	@Override
	public void addItem(OrderItem item) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "INSERT INTO orderItem value(?, ?, ?, ?, ?)";
		qr.update(DataSourceUtils.getConnection(), sql, item.getItemId(), item.getCount(), item.getSubtotal(), 
				item.getProduct().getPid(), item.getOrder().getOid()); 
	}

	/**
	 * ��ҳ��ѯ����
	 */
	@Override
	public List<Order> findAllByPage(int currPage, int pageSize, String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM orders WHERE uid = ? ORDER BY ordertime desc limit ?, ?";
		 List<Order> list = qr.query(sql, new BeanListHandler<>(Order.class), uid, (currPage-1)*pageSize, pageSize);
		 
		 //������������
		 sql = "SELECT * FROM orderitem oi, product p WHERE oi.pid = p.pid AND oi.oid = ?";
		 for (Order order : list) {
			 //��ǰ������������������
			 List<Map<String, Object>> mList = qr.query(sql, new MapListHandler(), order.getOid());
			 
			 for(Map<String, Object> map : mList) {
				 //��װProduct����
				 Product p = new Product();
				 BeanUtils.populate(p, map);
				 
				 //��װOrderItem
				 OrderItem oi = new OrderItem();
				 BeanUtils.populate(oi, map);
				 oi.setProduct(p);
				 
				 //��OrderItem������ӵ���Ӧ��Order�����List������
				 order.getItems().add(oi);
			 }
		}
		 return list;
	}

	/**
	 * ��ѯ������
	 */
	@Override
	public int getTotalCount(String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT count(*) FROM Orders WHERE uid = ?";
		return ((Long)qr.query(sql, new ScalarHandler(), uid)).intValue();
	}

	/**
	 * ͨ��oid��ѯ��������
	 */
	@Override
	public Order getById(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM orders WHERE oid = ?";
		Order order = (Order) qr.query(sql, new BeanHandler<>(Order.class), oid);
		
		sql = "SELECT * FROM orderitem oi, product p WHERE oi.pid = p.pid AND oi.oid = ?";
		List<Map<String, Object>> lists = qr.query(sql, new MapListHandler(), oid);
		for(Map<String, Object> list : lists) {
			//��װProduct����
			Product p = new Product();
			BeanUtils.populate(p, list);
			
			//��װOrderItem����
			OrderItem oi = new OrderItem();
			BeanUtils.populate(oi, list);
			oi.setProduct(p);
			
			//��OrderItem����Order��items��
			order.getItems().add(oi);
		}
			
		return order;
	}

	/**
	 * �޸Ķ���
	 */
	@Override
	public void update(Order order) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "Update orders set state = ?, address = ?, name = ?, telephone = ? WHERE oid = ?";
		qr.update(sql, order.getState(), order.getAddress(), order.getName(), order.getTelephone(), order.getOid());
	}

	/**
	 * ����state��ѯ����
	 */
	@Override
	public List<Order> findAllByState(String state) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//state == null(���ж���) 0(δ֧������)	1(��֧������)	2(�ѷ�������)
		String sql = "SELECT * FROM orders WHERE 1=1 ";	//��ѯ���ж���
		
		if(state != null && state.trim().length() > 0) {
			sql += "and state = ? ORDER BY ordertime DESC";
			return qr.query(sql, new BeanListHandler<>(Order.class), state);
		}
		sql += " ORDER BY ordertime DESC";
		return qr.query(sql, new BeanListHandler<>(Order.class));
	}

}
