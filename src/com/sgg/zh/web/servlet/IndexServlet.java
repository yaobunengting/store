package com.sgg.zh.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgg.zh.entity.Product;
import com.sgg.zh.service.ProductService;
import com.sgg.zh.service.impl.ProductServiceImpl;
import com.sgg.zh.utils.BeanFactory;

/**
 * 和首页相关的servlet
 */
@WebServlet("/index")
public class IndexServlet extends BaseServlet {
	
	ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
	
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 去数据库中查询最新商品和热门商品 将他没放入request域中,请求转发
		
		//最新商品
		List<Product> newList = null;
		//热门商品
		List<Product> hotList = null;
		try {
			newList = ps.findNew();
			
			hotList = ps.findHot();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//将两个list放入request域中
		request.setAttribute("newList", newList);
		request.setAttribute("hotList", hotList);
		return "/jsp/index.jsp";
	}

}
