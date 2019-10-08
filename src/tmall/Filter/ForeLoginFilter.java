package tmall.Filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import tmall.bean.User;
/*
 * 	过滤登录无关的界面，如果无关，放行
 */
public class ForeLoginFilter implements Filter{
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//1.强转
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		//2.准备一个字符串的数组，里面存放与登录无关的方法
		String[] noNeedLoginPage = new String[]{
				"homePage",
				"checkLogin",
				"register",
				"loginAjax",
				"login",
				"product",
				"category",
				"search"
		};
		//3.获取uri，去掉前缀，是否以/fore开头，并且不是以/foreServlet开头
		String contextPath = req.getServletContext().getContextPath();
		String uri = req.getRequestURI();
		uri = uri.replace(contextPath,"");
		//4.取出后面的字符串，看是否在字符串数组中
		if(uri.startsWith("/fore")&& !uri.startsWith("/foreServlet")) {
			String method = StringUtils.substringAfterLast(uri,"_");
			if(!Arrays.asList(noNeedLoginPage).contains(method)) {
				//5.如果不在，则需登录验证，如果user对象不存在，则跳转login.jsp
				User user = (User) req.getSession().getAttribute("user");
				if(user == null) {
					resp.sendRedirect("login.jsp");
					return;
				}
			}
		}
		//6.否则放行
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
