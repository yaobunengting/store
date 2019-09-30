package com.sgg.zh.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart implements Serializable{
	private Map<String, CartItem> map = new LinkedHashMap<>();	//存放购物车项的集合:key商品id cartItem:购物车项,使用map集合便于删除单个购物车项
	
	private Double total = 0.0;	//总金额
	
	/**
	 * 添加到购物车
	 * @param cartItem
	 */
	public void add2Cart(CartItem cartItem) {
		//1.判断购物车有无该商品
		String pid = cartItem.getProduct().getPid();
		if(map.containsKey(pid)) {
			//有
			//获取购物车中购物项
			CartItem oItem = map.get(pid);	
			//设置购买数量 需要获取该商品之前的购买数量+现在的购买数量(cartItem.getCount())
			oItem.setCount(oItem.getCount() + cartItem.getCount());
			
		} else {
			//无 将购物车添加进去
			map.put(pid, cartItem);
		}
		
		//2.添加完成之后 修改金额
		total += cartItem.getSubtotal();
	} 
	/**
	 * 从购物车删除指定购物车项
	 * @param pid
	 */
	public void removeFromCart(String pid) {
		//1.从集合中移除
		CartItem cartItem = map.remove(pid);
		//2.修改金额
		total -= cartItem.getSubtotal();
		
	}
	/**
	 * 清空购物车
	 */
	public void clearCart() {
		//1.map置空
		map.clear();
		//2.金额归零
		total = 0.0;
	}

	public Map<String, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	
}
