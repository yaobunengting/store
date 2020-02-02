package com.sgg.zh.web.servlet;

import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgg.zh.entity.Cart;
import com.sgg.zh.entity.CartItem;
import com.sgg.zh.entity.Order;
import com.sgg.zh.entity.OrderItem;
import com.sgg.zh.entity.PageBean;
import com.sgg.zh.entity.User;
import com.sgg.zh.service.OrderService;
import com.sgg.zh.utils.BeanFactory;
import com.sgg.zh.utils.PaymentUtil;
import com.sgg.zh.utils.UUIDUtils;

import net.sf.ehcache.search.expression.Or;

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
	
	/**
	 * 分页查询我的订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String findAllByPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取当前页
		int currPage = 0;
		if (null == request.getParameter("currPage")) {
			currPage = 1;
		} else {
			currPage = Integer.parseInt(request.getParameter("currPage"));	
		}
		int pageSize = 3;
		
		//2.获取用户
		User user = (User) request.getSession().getAttribute("user");
		if(null == user) {
			request.setAttribute("msg", "您还没有登录,请登录");
			return "/jsp/msg.jsp";
		}
		//3.调用service,分页查询 参数:currPage, pageSize, user;返回值PageBean
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		PageBean<Order> pb = os.findAllByPage(currPage, pageSize, user);
		
		//4.将PageBean放入request域中
		request.setAttribute("pb", pb);
		//5.请求转发
		return "/jsp/order_list.jsp";
	}
	
	/**
	 * 查看订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取oid
		String oid = request.getParameter("oid");
		
		//2.调用service通过oid获取order对象
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order = os.getById(oid);

		//3.将order对象放入域中
		request.setAttribute("order", order);
		
		
		return "/jsp/order_info.jsp";
	}
	
	public String pay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//接受参数
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		String oid = request.getParameter("oid");

		//通过id获取order
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order = os.getById(oid);

		order.setAddress(address);
		order.setName(name);
		order.setTelephone(telephone);
		
		//更新order
		os.update(order);
		
		//组织发送支付公司需要的数据
		String pd_FrpId = request.getParameter("pd_FrpId");	//银行通道
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
		String p2_Order = oid;
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		//支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		//第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		
		//加密hmac需要密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
		
		//发送给第三方
		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);
		
		response.sendRedirect(sb.toString());
		
		return null;
		
	}
	
	public String updateState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取oid
		String oid = request.getParameter("oid");
		
		//调用service修改订单状态
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order = os.getById(oid);
		
		order.setState(3);
		os.update(order);
		
		//重定向
		response.sendRedirect(request.getContextPath() + "/order?method=findAllByPage&currPage=1");
		return null;
	}

}
