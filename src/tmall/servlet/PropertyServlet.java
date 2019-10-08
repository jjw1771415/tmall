package tmall.servlet;

import java.util.List;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.Page;
@WebServlet("/propertyServlet")
public class PropertyServlet extends BaseBackServlet{

	private static final long serialVersionUID = 1L;

	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		response.setContentType("text/html;charset=utf-8");
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(cid);
		String name = request.getParameter("name");
		Property p = new Property();
		p.setCategory(c);
		p.setName(name);
		propertyDAO.add(p);
		return "@admin_property_list?cid="+cid;
	}

	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Property pt = propertyDAO.get(id);
		//先通过属性id删除对应属性值
		propertyValueDAO.deleteByProperty(id);
		//再删除属性
		propertyDAO.delete(id);
		return "@admin_property_list?cid="+pt.getCategory().getId();
	}

	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(cid);
		Property pt = new Property();
		pt.setCategory(c);
		pt.setId(id);
		pt.setName(name);
		propertyDAO.update(pt);
		return "@admin_property_list?cid="+pt.getCategory().getId();
	}

	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Property pt = propertyDAO.get(id);
		pt.setId(id);
		request.setAttribute("pt",pt);
		return "admin/editProperty.jsp";
	}

	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(cid);
		List<Property> pts = propertyDAO.list(cid, page.getStart(), page.getCount());
		int total = propertyDAO.getTotal(cid);
		page.setTotal(total);
		page.setParam("&cid="+c.getId());
		request.setAttribute("pts",pts);
		request.setAttribute("page",page);
		request.setAttribute("c",c);
		return "admin/listProperty.jsp";
	}
}
