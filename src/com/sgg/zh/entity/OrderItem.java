package com.sgg.zh.entity;

import java.io.Serializable;

/**
 * ������ʵ��
 * @author Administrator
 *
 */
public class OrderItem implements Serializable{
	
	private String itemId;	//������id
	private Integer count;	//����
	private Double subtotal;	//С��
	
	private Product product;	//�����ĸ���Ʒ
	
	private Order order;	//�����Ǹ�����

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
