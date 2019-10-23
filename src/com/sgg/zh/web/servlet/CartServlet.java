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
	 * 获取购物车
	 * @param request
	 * @return
	 */
	public Cart getCart(HttpServletRequest request) {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		
		//判断购物车是否为空
		if(cart == null) {
			//创建一个cart
			cart = (Cart) BeanFactory.getBean("Cart");
			//添加到Session域中
			request.getSession().setAttribute("cart", cart);
		}
		return cart;
	}

    /**
     * 添加到购物车
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("xxxxxxx");
		//获取pid和数量
		String pid = request.getParameter("pid");
		int count = Integer.parseInt(request.getParameter("count"));
		try {
			
			//调用ProductService 通过pid获取一个商品
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			Product product = ps.getById(pid);
			
			//组装成CartItem
			CartItem cartItem = (CartItem) BeanFactory.getBean("CartItem");
			
			//添加到购物车
			cartItem.setProduct(product);
			cartItem.setCount(count);
			//添加到购物车中
			Cart cart = getCart(request);
			cart.add2Cart(cartItem);
			//重定向
			response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 从购物车中移除购物项
	 * @param request
	 * @param response
	 * @return
	 */
	public String remove(HttpServletRequest request, HttpServletResponse response) {
		//1.获取商品的pid
		String pid = request.getParameter("pid");
		
		//2.调用购物车的remove方法
		getCart(request).removeFromCart(pid);
		
		//重定向
		try {
			response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 清空购物车
	 * @param request
	 * @param response
	 * @return
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response) {
		//获取购物车 清空
		getCart(request).clearCart();
		
		//重定向
		try {
			response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
