package tmall.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import tmall.dao.CategoryDAO;
import tmall.dao.OrderDAO;
import tmall.dao.OrderItemDAO;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
import tmall.dao.PropertyDAO;
import tmall.dao.PropertyValueDAO;
import tmall.dao.ReviewDAO;
import tmall.dao.UserDAO;
import tmall.util.Page;

public abstract class BaseBackServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	//抽象方法
	public abstract String add(HttpServletRequest request,HttpServletResponse response,Page page);
	public abstract String delete(HttpServletRequest request,HttpServletResponse response,Page page);
	public abstract String update(HttpServletRequest request,HttpServletResponse response,Page page);
	public abstract String edit(HttpServletRequest request,HttpServletResponse response,Page page);
	public abstract String list(HttpServletRequest request,HttpServletResponse response,Page page);
	//DAO对象
	protected CategoryDAO categoryDAO = new CategoryDAO();
	protected OrderDAO orderDAO = new OrderDAO();
	protected OrderItemDAO orderItemDAO = new OrderItemDAO();
	protected ProductDAO productDAO = new ProductDAO();
	protected ProductImageDAO productImageDAO = new ProductImageDAO();
	protected PropertyDAO propertyDAO = new PropertyDAO();
	protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
	protected ReviewDAO reviewDAO = new ReviewDAO();
	protected UserDAO userDAO = new UserDAO();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//获取分页信息
			int start = 0;
			int count = 5;
			try{
				start = Integer.parseInt(request.getParameter("page.start"));
			}catch(Exception e) {}
			try{
				count = Integer.parseInt(request.getParameter("page.count"));
			}catch(Exception e) {}
			Page page = new Page(start,count);
			
			//通过反射调用方法
			String method =(String) request.getAttribute("method");
			Method m = this.getClass().getMethod(method,HttpServletRequest.class,HttpServletResponse.class,Page.class);
			String ret = m.invoke(this,request,response,page).toString();
			if(ret.startsWith("@")) {
				response.sendRedirect(ret.substring(1));
			}
			else if(ret.startsWith("%")) {
				response.getWriter().print(ret.substring(1));
			}
			else {
				request.getRequestDispatcher(ret).forward(request, response);
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	//上传文件
	public InputStream parseUpload(HttpServletRequest request,Map<String,String> params) {
		InputStream is = null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			//设置上传文件的大小限制为10M
			factory.setSizeThreshold(1024*10240);
			
			List items = null;
			items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while(iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if(!item.isFormField()) {
					is = item.getInputStream();
				}else {
					String param = item.getFieldName();
					String value = item.getString();
					value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
					params.put(param, value);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return is;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
