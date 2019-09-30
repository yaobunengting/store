package com.sgg.zh.entity;

import java.io.Serializable;

/**
 * ���ﳵ��ʵ��
 * @author Administrator
 *
 */
public class CartItem implements Serializable{
	private Product product;	//��Ʒ����
	private Integer count;	//��Ʒ����
	private Double subtotal = 0.0;	//С��
	

	public CartItem() {}
	public CartItem(Product product, Integer count) {
		this.product = product;
		this.count = count;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getSubtotal() {
		return product.getShop_price() * count;
	}
	
	
	
}
