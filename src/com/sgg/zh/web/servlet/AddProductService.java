package com.sgg.zh.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.sgg.zh.entity.Category;
import com.sgg.zh.entity.Product;
import com.sgg.zh.service.ProductService;
import com.sgg.zh.utils.BeanFactory;
import com.sgg.zh.utils.UUIDUtils;
import com.sgg.zh.utils.UploadUtils;

/**
 * Servlet implementation class AddProductService
 */
@WebServlet("/addProduct")
public class AddProductService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		//创建一个Map集合
		Map<String, String> map = new HashMap<>();
		
		try {
			//创建磁盘文件项工厂
			DiskFileItemFactory factory =  new DiskFileItemFactory();
			//创建核心上传对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解析request
			List<FileItem> list = upload.parseRequest(request);
			
			//遍历集合
			for (FileItem fi : list) {
				//判断是否是普通的上传对象
				if(fi.isFormField()) {
					//普通上传组件
					map.put(fi.getFieldName(), fi.getString("utf-8"));
				}else {
					//文件上传组件
					
					//获取文件的名称
					String name = fi.getName();
					
					//获取文件的真是名称 	xxx.xx
					String realName = UploadUtils.getRealName(name);
					
					//获取文件随机名称
					String uuidName = UploadUtils.getUUIDName(realName);
					
					//获取文件存放飞路径
					String path = this.getServletContext().getRealPath("/products/1");
					
					//获取文件流
					InputStream is = fi.getInputStream();
					
					//保存图片
					FileOutputStream fos = new FileOutputStream(new File(path, uuidName));
					IOUtils.copy(is, fos);
					fos.close();
					is.close();
					
					//删除临时文件
					fi.delete();
					//在map中设置文件的路径
					map.put(fi.getFieldName(), "products/1/" + uuidName);
				}
				
			}
			
			//封装参数
			Product p = new Product();
			BeanUtils.populate(p, map);
			
			//封装商品id
			p.setPid(UUIDUtils.getId());
			
			//封装商品时间
			p.setPdate(new Date());
			
			//封装商品分类
			Category category = new Category();
			category.setCid((String)map.get("cid"));
			p.setCategory(category);
			//调用service完成添加
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.add(p);
			
			//页面重定向
			response.sendRedirect(request.getContextPath() + "/adminProduct?method=findAll");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msg", "商品添加失败");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
			return;
		}
		
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		doGet(request, response);
	}

	
}
