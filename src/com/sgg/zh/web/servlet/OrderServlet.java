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
	
	/**
	 * ��ҳ��ѯ�ҵĶ���
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String findAllByPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.��ȡ��ǰҳ
		int currPage = 0;
		if (null == request.getParameter("currPage")) {
			currPage = 1;
		} else {
			currPage = Integer.parseInt(request.getParameter("currPage"));	
		}
		int pageSize = 3;
		
		//2.��ȡ�û�
		User user = (User) request.getSession().getAttribute("user");
		if(null == user) {
			request.setAttribute("msg", "����û�е�¼,���¼");
			return "/jsp/msg.jsp";
		}
		//3.����service,��ҳ��ѯ ����:currPage, pageSize, user;����ֵPageBean
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		PageBean<Order> pb = os.findAllByPage(currPage, pageSize, user);
		
		//4.��PageBean����request����
		request.setAttribute("pb", pb);
		//5.����ת��
		return "/jsp/order_list.jsp";
	}
	
	/**
	 * �鿴��������
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.��ȡoid
		String oid = request.getParameter("oid");
		
		//2.����serviceͨ��oid��ȡorder����
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order = os.getById(oid);

		//3.��order�����������
		request.setAttribute("order", order);
		
		
		return "/jsp/order_info.jsp";
	}
	
	public String pay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//���ܲ���
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		String oid = request.getParameter("oid");

		//ͨ��id��ȡorder
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order = os.getById(oid);

		order.setAddress(address);
		order.setName(name);
		order.setTelephone(telephone);
		
		//����order
		os.update(order);
		
		//��֯����֧����˾��Ҫ������
		String pd_FrpId = request.getParameter("pd_FrpId");	//����ͨ��
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
		String p2_Order = oid;
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		//֧���ɹ��ص���ַ ---- ������֧����˾����ʡ��û�����
		//������֧�����Է�����ַ
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		
		//����hmac��Ҫ��Կ
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
		
		//���͸�������
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
		//��ȡoid
		String oid = request.getParameter("oid");
		
		//����service�޸Ķ���״̬
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order = os.getById(oid);
		
		order.setState(3);
		os.update(order);
		
		//�ض���
		response.sendRedirect(request.getContextPath() + "/order?method=findAllByPage&currPage=1");
		return null;
	}

}
