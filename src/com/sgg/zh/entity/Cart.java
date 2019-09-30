package com.sgg.zh.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart implements Serializable{
	private Map<String, CartItem> map = new LinkedHashMap<>();	//��Ź��ﳵ��ļ���:key��Ʒid cartItem:���ﳵ��,ʹ��map���ϱ���ɾ���������ﳵ��
	
	private Double total = 0.0;	//�ܽ��
	
	/**
	 * ��ӵ����ﳵ
	 * @param cartItem
	 */
	public void add2Cart(CartItem cartItem) {
		//1.�жϹ��ﳵ���޸���Ʒ
		String pid = cartItem.getProduct().getPid();
		if(map.containsKey(pid)) {
			//��
			//��ȡ���ﳵ�й�����
			CartItem oItem = map.get(pid);	
			//���ù������� ��Ҫ��ȡ����Ʒ֮ǰ�Ĺ�������+���ڵĹ�������(cartItem.getCount())
			oItem.setCount(oItem.getCount() + cartItem.getCount());
			
		} else {
			//�� �����ﳵ��ӽ�ȥ
			map.put(pid, cartItem);
		}
		
		//2.������֮�� �޸Ľ��
		total += cartItem.getSubtotal();
	} 
	/**
	 * �ӹ��ﳵɾ��ָ�����ﳵ��
	 * @param pid
	 */
	public void removeFromCart(String pid) {
		//1.�Ӽ������Ƴ�
		CartItem cartItem = map.remove(pid);
		//2.�޸Ľ��
		total -= cartItem.getSubtotal();
		
	}
	/**
	 * ��չ��ﳵ
	 */
	public void clearCart() {
		//1.map�ÿ�
		map.clear();
		//2.������
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
