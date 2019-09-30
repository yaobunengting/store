package com.sgg.zh.entity;

import java.io.Serializable;

/**
 * 购物车项实体
 * @author Administrator
 *
 */
public class CartItem implements Serializable{
	private Product product;	//商品对象
	private Integer count;	//商品数量
	private Double subtotal = 0.0;	//小计
	

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
