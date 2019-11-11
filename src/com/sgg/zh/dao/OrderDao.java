package com.sgg.zh.dao;

import java.util.List;

import com.sgg.zh.entity.Order;
import com.sgg.zh.entity.OrderItem;

public interface OrderDao {

	void add(Order order) throws Exception;

	void addItem(OrderItem item) throws Exception;

	List<Order> findAllByPage(int currPage, int pageSize, String uid) throws Exception;

	int getTotalCount(String uid) throws Exception;

	Order getById(String oid) throws Exception;

}
