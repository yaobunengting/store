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
 * ����ҳ��ص�servlet
 */
@WebServlet("/index")
public class IndexServlet extends BaseServlet {
	
	ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
	
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ȥ���ݿ��в�ѯ������Ʒ��������Ʒ ����û����request����,����ת��
		
		//������Ʒ
		List<Product> newList = null;
		//������Ʒ
		List<Product> hotList = null;
		try {
			newList = ps.findNew();
			
			hotList = ps.findHot();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//������list����request����
		request.setAttribute("newList", newList);
		request.setAttribute("hotList", hotList);
		return "/jsp/index.jsp";
	}

}
