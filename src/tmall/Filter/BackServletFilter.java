package tmall.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 	拦截浏览器访问后台的路径
 * @author jjw1771415
 *
 */
public class BackServletFilter implements Filter{
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//1.转化request和response
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		//2.获取上下文路径
		String contextPath = req.getServletContext().getContextPath();
		//3.获取uri
		String uri = req.getRequestURI();
		//4.替换掉uri中的上下文路径
		uri = uri.replace(contextPath,"");
		//5.判断uri是否以/admin_开头，如果是，则跳转到相应页面，截取出方法名
		if(uri.startsWith("/admin_")) {
			String[] strings = uri.split("_");
			String servletPath = strings[1];
			String method = strings[2];
			//将method存到request域中
			req.setAttribute("method",method);
			//跳转到相应的Servlet
			req.getRequestDispatcher("/"+servletPath+"Servlet").forward(req, resp);
			return;
		}
		//6.不是，放行
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
