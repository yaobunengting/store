package com.sgg.zh.entity;

import java.io.Serializable;

/**
 * 订单项实体
 * @author Administrator
 *
 */
public class OrderItem implements Serializable{
	
	private String itemId;	//订单项id
	private Integer count;	//数量
	private Double subtotal;	//小计
	
	private Product product;	//包含哪个商品
	
	private Order order;	//属于那个订单

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}
