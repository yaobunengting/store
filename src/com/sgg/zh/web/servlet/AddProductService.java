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
		//����һ��Map����
		Map<String, String> map = new HashMap<>();
		
		try {
			//���������ļ����
			DiskFileItemFactory factory =  new DiskFileItemFactory();
			//���������ϴ�����
			ServletFileUpload upload = new ServletFileUpload(factory);
			//����request
			List<FileItem> list = upload.parseRequest(request);
			
			//��������
			for (FileItem fi : list) {
				//�ж��Ƿ�����ͨ���ϴ�����
				if(fi.isFormField()) {
					//��ͨ�ϴ����
					map.put(fi.getFieldName(), fi.getString("utf-8"));
				}else {
					//�ļ��ϴ����
					
					//��ȡ�ļ�������
					String name = fi.getName();
					
					//��ȡ�ļ����������� 	xxx.xx
					String realName = UploadUtils.getRealName(name);
					
					//��ȡ�ļ��������
					String uuidName = UploadUtils.getUUIDName(realName);
					
					//��ȡ�ļ���ŷ�·��
					String path = this.getServletContext().getRealPath("/products/1");
					
					//��ȡ�ļ���
					InputStream is = fi.getInputStream();
					
					//����ͼƬ
					FileOutputStream fos = new FileOutputStream(new File(path, uuidName));
					IOUtils.copy(is, fos);
					fos.close();
					is.close();
					
					//ɾ����ʱ�ļ�
					fi.delete();
					//��map�������ļ���·��
					map.put(fi.getFieldName(), "products/1/" + uuidName);
				}
				
			}
			
			//��װ����
			Product p = new Product();
			BeanUtils.populate(p, map);
			
			//��װ��Ʒid
			p.setPid(UUIDUtils.getId());
			
			//��װ��Ʒʱ��
			p.setPdate(new Date());
			
			//��װ��Ʒ����
			Category category = new Category();
			category.setCid((String)map.get("cid"));
			p.setCategory(category);
			//����service������
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.add(p);
			
			//ҳ���ض���
			response.sendRedirect(request.getContextPath() + "/adminProduct?method=findAll");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msg", "��Ʒ���ʧ��");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
			return;
		}
		
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		doGet(request, response);
	}

	
}
