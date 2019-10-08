package tmall.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class BaseForeServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

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
			int count = 10;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
