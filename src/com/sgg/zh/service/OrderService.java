package com.sgg.zh.service;

import java.util.List;

import com.sgg.zh.entity.Order;
import com.sgg.zh.entity.PageBean;
import com.sgg.zh.entity.User;

public interface OrderService {

	void add(Order order) throws Exception;

	PageBean<Order> findAllByPage(int currPage, int pageSize, User user) throws Exception;

	Order getById(String oid) throws Exception;

	void update(Order order) throws Exception;

	List<Order> findAllByState(String state) throws Exception;

}
