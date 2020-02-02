package com.sgg.zh.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgg.zh.entity.Order;
import com.sgg.zh.entity.OrderItem;
import com.sgg.zh.service.OrderService;
import com.sgg.zh.utils.BeanFactory;
import com.sgg.zh.utils.JsonUtils;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * Servlet implementation class AdminOrderServlet
 */
@WebServlet("/adminOrder")
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	OrderService os = (OrderService) BeanFactory.getBean("OrderService");
	/**
	 * ��ѯ����
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findAllByState(HttpServletRequest request , HttpServletResponse response) throws Exception {
		//����state
		String state = request.getParameter("state");
		
		//����service
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		List<Order> list = os.findAllByState(state);
		
		//��list��������,����ת��
		request.setAttribute("list", list);
		return "/admin/order/list.jsp";
	}
	
	
	/**
	 * ��ѯ��������
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getDetailByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		
		//����oid
		String oid = request.getParameter("oid");
		
		//����service��ѯ��������	����ֵlist<OrderItem>
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		List<OrderItem> items = os.getById(oid).getItems();
		
		//��listת��json д��
		//�ų����õ�����(��������Щ����)
		JsonConfig config = JsonUtils.configJson(new String[] {"class", "itemid", "order"});
		JSONArray json = JSONArray.fromObject(items, config);
		
		response.getWriter().println(json);
		
		return null;
	}
	
	/**
	 * �޸Ķ���״̬
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String updateState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//����oid��state
		String oid = request.getParameter("oid");
		String state = request.getParameter("state");
		
		//����service
		Order order = os.getById(oid);
		order.setState(Integer.valueOf(state));
		
		os.update(order);
		
		//ҳ���ض���
		response.sendRedirect(request.getContextPath() + "/adminOrder?method=findAllByState&state=1");
		return null;
	}

}
