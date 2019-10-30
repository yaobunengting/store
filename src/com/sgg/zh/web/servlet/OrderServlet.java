package com.sgg.zh.web.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgg.zh.entity.Cart;
import com.sgg.zh.entity.CartItem;
import com.sgg.zh.entity.Order;
import com.sgg.zh.entity.OrderItem;
import com.sgg.zh.entity.User;
import com.sgg.zh.service.OrderService;
import com.sgg.zh.utils.BeanFactory;
import com.sgg.zh.utils.UUIDUtils;

/**
 * ����ģ��
 */
@WebServlet("/order")
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * ���ɶ���
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//0.�ж��û��Ƿ��¼
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			request.setAttribute("msg", "���ȵ�¼");
			return "/jsp/msg.jsp";
		}
		//1.��װ����
		Order order = new Order();
		//1.1����id
		order.setOid(UUIDUtils.getId());
		//1.2����ʱ��
		order.setOrdetTime(new Date());
		//1.3�ܽ��
		//��ȡsession�е�cart
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		System.out.println("xxxx:" + cart);
		order.setTotal(cart.getTotal());
		
		//1.4���������ж�����
		/*
		 * �Ȼ�ȡcart��items
		 * ����items ��װ��OrderItem
		 * ��OrderItem��ӵ�list(items)��
		 */
		Collection<CartItem> cartItems = cart.getItems();
		for (CartItem cartItem : cartItems) {
			OrderItem oi = new OrderItem();
			
			//����id
			oi.setItemId(UUIDUtils.getId());
			
			//���ù�������
			oi.setCount(cartItem.getCount());
			//����С��
			oi.setSubtotal(cartItem.getSubtotal());
			//����product
			oi.setProduct(cartItem.getProduct());
			//����order
			oi.setOrder(order);
			
			//��ӵ�list��
			order.getItems().add(oi);
		}
		
		
		//1.5�����û�
		order.setUser(user);
		//2.����service,��Ӷ���
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		os.add(order);
		//3.��order����request����,����ת��
		request.setAttribute("order", order);
		
		//��չ��ﳵ
		request.getSession().removeAttribute("cart");
		return "/jsp/order_info.jsp";
	}
	
	
	public String find

}
