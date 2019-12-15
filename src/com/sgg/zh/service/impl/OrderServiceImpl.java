package com.sgg.zh.service.impl;

import java.util.List;

import com.sgg.zh.dao.OrderDao;
import com.sgg.zh.entity.Order;
import com.sgg.zh.entity.OrderItem;
import com.sgg.zh.entity.PageBean;
import com.sgg.zh.entity.User;
import com.sgg.zh.service.OrderService;
import com.sgg.zh.utils.BeanFactory;
import com.sgg.zh.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {

	@Override
	public void add(Order order) throws Exception {
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();
			
			OrderDao od = (OrderDao) new BeanFactory().getBean("OrderDao");
			//2.向order表中添加一个数据
			od.add(order);
			
			
			//3.向orderItem中添加n条数据
			for (OrderItem item : order.getItems()) {
				od.addItem(item);
			}
			//4.事务处理
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//事务回滚
			DataSourceUtils.rollbackAndClose();
			
			throw e;
		}
		
	}

	/**
	 * 分页查询订单
	 */
	@Override
	public PageBean<Order> findAllByPage(int currPage, int pageSize, User user) throws Exception {
		//查询当前页数据
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		List<Order> list = od.findAllByPage(currPage, pageSize, user.getUid());
		//查询总条数
		int totalCount = od.getTotalCount(user.getUid());
		
		return new PageBean<Order>(list, currPage, pageSize, totalCount);
	}

	/**
	 * 查看订单详情
	 */
	@Override
	public Order getById(String oid) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		return od.getById(oid);
	}

	@Override
	public void update(Order order) throws Exception {
		// TODO Auto-generated method stub
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		od.update(order);
	}

}
