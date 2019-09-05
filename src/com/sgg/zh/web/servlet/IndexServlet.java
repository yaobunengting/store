package com.sgg.zh.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgg.zh.Entity.Category;
import com.sgg.zh.service.CategoryService;
import com.sgg.zh.service.impl.CategoryServiceImpl;

/**
 * 和首页相关的servlet
 */
@WebServlet("/index")
public class IndexServlet extends BaseServlet {

	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 去数据库中查询最新商品和热门商品 将他没放入request域中,请求转发
		return "/jsp/index.jsp";
	}

}
