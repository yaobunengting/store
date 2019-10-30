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
 * 订单模块
 */
@WebServlet("/order")
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 生成订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//0.判断用户是否登录
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			request.setAttribute("msg", "请先登录");
			return "/jsp/msg.jsp";
		}
		//1.封装数据
		Order order = new Order();
		//1.1订单id
		order.setOid(UUIDUtils.getId());
		//1.2订单时间
		order.setOrdetTime(new Date());
		//1.3总金额
		//获取session中的cart
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		System.out.println("xxxx:" + cart);
		order.setTotal(cart.getTotal());
		
		//1.4订单的所有订单项
		/*
		 * 先获取cart中items
		 * 遍历items 组装成OrderItem
		 * 将OrderItem添加到list(items)中
		 */
		Collection<CartItem> cartItems = cart.getItems();
		for (CartItem cartItem : cartItems) {
			OrderItem oi = new OrderItem();
			
			//设置id
			oi.setItemId(UUIDUtils.getId());
			
			//设置购买数量
			oi.setCount(cartItem.getCount());
			//设置小计
			oi.setSubtotal(cartItem.getSubtotal());
			//设置product
			oi.setProduct(cartItem.getProduct());
			//设置order
			oi.setOrder(order);
			
			//添加到list中
			order.getItems().add(oi);
		}
		
		
		//1.5设置用户
		order.setUser(user);
		//2.调用service,添加订单
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		os.add(order);
		//3.将order放入request域中,请求转发
		request.setAttribute("order", order);
		
		//清空购物车
		request.getSession().removeAttribute("cart");
		return "/jsp/order_info.jsp";
	}
	
	
	public String find

}
