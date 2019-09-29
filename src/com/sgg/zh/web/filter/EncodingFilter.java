package com.sgg.zh.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一编码
 * @author Administrator
 *
 */
public class EncodingFilter implements Filter {
	
	@Override
		public void init(FilterConfig filterConfig) throws ServletException {
			// TODO Auto-generated method stub
		}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		//1.强转
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		//2.放行
		chain.doFilter(new MyRequest(request), response);
		
	}

}

class MyRequest extends HttpServletRequestWrapper {
	private HttpServletRequest request;
	private boolean flag = true;

	public MyRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	
	
	
	
	@Override
	public String getParameter(String name) {
		if(name == null || name.trim().length() == 0) {
			return null;
		}
		
		String[] values = getParameterValues(name);
		if(values == null || values.length == 0) {
			return null;
		}
		
		return values[0];
	}
	
	@Override
	public String[] getParameterValues(String name) {
		if(name == null || name.trim().length() == 0) {
			return null;
		}
		
		Map<String, String[]> map = getParameterMap();
		if(map == null || map.size() == 0) {
			return null;
		}
		
		return map.get(name);
	}
	
	/**
	 * 首先判断请求方式
	 * 若为post  request.setCharacterEncoding("utf-8")
	 * 若为get  将map中的值遍历编码就可以了
	 */
	@Override
	public Map<String, String[]> getParameterMap() {
		String method = request.getMethod();
		if("post".equalsIgnoreCase(method)) {
			try {
				request.setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("get".equalsIgnoreCase(method)) {
			Map<String, String[]> map = request.getParameterMap();
			if(flag) {
				for (String key : map.keySet()) {
					//继续遍历数组
					String[] arr = map.get(key);
					
					for(int i = 0; i < arr.length; i++) {
						//编码
						try {
							arr[i] = new String(arr[i].getBytes("iso8859-1"), "utf-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				flag = false;
			}
			return map;
		}
		

		return super.getParameterMap();
	}
	
}
