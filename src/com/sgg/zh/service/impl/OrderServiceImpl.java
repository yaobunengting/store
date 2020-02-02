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
	private OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
	/**
	 * ��Ӷ���
	 */
	@Override
	public void add(Order order) throws Exception {
		try {
			//1.��������
			DataSourceUtils.startTransaction();
			
			//2.��order�������һ������
			od.add(order);
			
			
			//3.��orderItem�����n������
			for (OrderItem item : order.getItems()) {
				od.addItem(item);
			}
			//4.������
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//����ع�
			DataSourceUtils.rollbackAndClose();
			
			throw e;
		}
		
	}

	/**
	 * ��ҳ��ѯ����
	 */
	@Override
	public PageBean<Order> findAllByPage(int currPage, int pageSize, User user) throws Exception {
		//��ѯ��ǰҳ����
		List<Order> list = od.findAllByPage(currPage, pageSize, user.getUid());
		//��ѯ������
		int totalCount = od.getTotalCount(user.getUid());
		
		return new PageBean<Order>(list, currPage, pageSize, totalCount);
	}

	/**
	 * �鿴��������
	 */
	@Override
	public Order getById(String oid) throws Exception {
		return od.getById(oid);
	}

	/**
	 * ���¶���
	 */
	@Override
	public void update(Order order) throws Exception {
		od.update(order);
	}

	/**
	 * ����state��ѯ����
	 */
	@Override
	public List<Order> findAllByState(String state) throws Exception {
		return od.findAllByState(state);
	}

}
