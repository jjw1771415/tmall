package tmall.Filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import tmall.bean.Category;
import tmall.bean.OrderItem;
import tmall.bean.User;
import tmall.dao.CategoryDAO;
import tmall.dao.OrderItemDAO;

public class ForeServletFilter implements Filter{
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//1.强转request和response
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		//2.获取上下文路径
		String contextPath = req.getServletContext().getContextPath();
		req.getServletContext().setAttribute("contextPath",contextPath);
		
		User user = (User) req.getSession().getAttribute("user");
		int cartItemTotalNumber = 0;
		if(user != null) {
			List<OrderItem> ois = new OrderItemDAO().listByUser(user.getId());
			for (OrderItem oi : ois) {
				cartItemTotalNumber += oi.getNumber();
			}
		}
		req.setAttribute("cartItemTotalNumber", cartItemTotalNumber);
		
		@SuppressWarnings("unchecked")
		List<Category> cs = (List<Category>) req.getAttribute("cs");
		if(cs == null) {
			cs = new CategoryDAO().list();
			req.setAttribute("cs", cs);
		}
		
		//3.获取uri
		String uri = req.getRequestURI();
		//4.替换
		uri = uri.replace(contextPath,"");
		//5.判断替换后的uri是否以/fore开头并且不是以/foreServlet开头
		if(uri.startsWith("/fore") && !uri.startsWith("/foreServlet")) {
			//6.如果以/fore开头，则获取参数并跳转页面
			String method = StringUtils.substringAfterLast(uri,"_");
			req.setAttribute("method",method);
			req.getRequestDispatcher("/foreServlet").forward(req, resp);
			return;
		}
		//7.不是则放行
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
