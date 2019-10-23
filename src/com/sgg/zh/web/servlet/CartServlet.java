package com.sgg.zh.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgg.zh.entity.Cart;
import com.sgg.zh.entity.CartItem;
import com.sgg.zh.entity.Product;
import com.sgg.zh.service.ProductService;
import com.sgg.zh.utils.BeanFactory;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/cart")
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * ��ȡ���ﳵ
	 * @param request
	 * @return
	 */
	public Cart getCart(HttpServletRequest request) {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		
		//�жϹ��ﳵ�Ƿ�Ϊ��
		if(cart == null) {
			//����һ��cart
			cart = (Cart) BeanFactory.getBean("Cart");
			//��ӵ�Session����
			request.getSession().setAttribute("cart", cart);
		}
		return cart;
	}

    /**
     * ��ӵ����ﳵ
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("xxxxxxx");
		//��ȡpid������
		String pid = request.getParameter("pid");
		int count = Integer.parseInt(request.getParameter("count"));
		try {
			
			//����ProductService ͨ��pid��ȡһ����Ʒ
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			Product product = ps.getById(pid);
			
			//��װ��CartItem
			CartItem cartItem = (CartItem) BeanFactory.getBean("CartItem");
			
			//��ӵ����ﳵ
			cartItem.setProduct(product);
			cartItem.setCount(count);
			//��ӵ����ﳵ��
			Cart cart = getCart(request);
			cart.add2Cart(cartItem);
			//�ض���
			response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * �ӹ��ﳵ���Ƴ�������
	 * @param request
	 * @param response
	 * @return
	 */
	public String remove(HttpServletRequest request, HttpServletResponse response) {
		//1.��ȡ��Ʒ��pid
		String pid = request.getParameter("pid");
		
		//2.���ù��ﳵ��remove����
		getCart(request).removeFromCart(pid);
		
		//�ض���
		try {
			response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * ��չ��ﳵ
	 * @param request
	 * @param response
	 * @return
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response) {
		//��ȡ���ﳵ ���
		getCart(request).clearCart();
		
		//�ض���
		try {
			response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
