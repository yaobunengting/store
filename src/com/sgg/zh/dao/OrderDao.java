package com.sgg.zh.dao;

import com.sgg.zh.entity.Order;
import com.sgg.zh.entity.OrderItem;

public interface OrderDao {

	void add(Order order) throws Exception;

	void addItem(OrderItem item) throws Exception;

}
