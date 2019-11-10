package com.sgg.zh.service;

import com.sgg.zh.entity.Order;
import com.sgg.zh.entity.PageBean;
import com.sgg.zh.entity.User;

public interface OrderService {

	void add(Order order) throws Exception;

	PageBean<Order> findAllByPage(int currPage, int pageSize, User user) throws Exception;

}
