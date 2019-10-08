package tmall.servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Order;
import tmall.dao.OrderDAO;
import tmall.util.Page;
@WebServlet("/orderServlet")
public class OrderServlet extends BaseBackServlet{

	private static final long serialVersionUID = 1L;

	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}

	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}

	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}

	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}

	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<Order> os = orderDAO.list(page.getStart(), page.getCount());
		orderItemDAO.fill(os);
		
		int total = orderDAO.getTotal();
		page.setTotal(total);
		
		request.setAttribute("os",os);
		request.setAttribute("page",page);
		return "admin/listOrder.jsp";
	}
	
	public String delivery(HttpServletRequest request,HttpServletResponse response,Page page) {
		//获取订单id
		int id = Integer.parseInt(request.getParameter("id"));
		//通过id获取订单对象
		Order o = orderDAO.get(id);
		//设置订单的发货时间
		o.setDeliveryDate(new Date());
		//设置订单的状态
		o.setStatus(OrderDAO.waitConfirm);
		//修改数据库
		orderDAO.update(o);
		//重定向到listOrder.jsp页面
		return "@admin_order_list";
	}

}
