package tmall.servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.PropertyValue;
import tmall.util.Page;
@WebServlet("/productServlet")
public class ProductServlet extends BaseBackServlet{

	private static final long serialVersionUID = 1L;

	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDAO.get(cid);
         
        String name= request.getParameter("name");
        String subTitle= request.getParameter("subTitle");
        float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        int stock = Integer.parseInt(request.getParameter("stock"));
 
        Product p = new Product();
 
        p.setCategory(c);
        p.setName(name);
        p.setSubTitle(subTitle);
        p.setOriginalPrice(originalPrice);
        p.setPromotePrice(promotePrice);
        p.setStock(stock);
        p.setCreateDate(new Date());
 
        productDAO.add(p);
        return "@admin_product_list?cid="+cid;
	}

	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		productDAO.delete(pid);
		return "@admin_product_list?cid="+p.getCategory().getId();
	}

	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDAO.get(cid);
         
        int id = Integer.parseInt(request.getParameter("id"));
        String name= request.getParameter("name");
        String subTitle= request.getParameter("subTitle");
        float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        int stock = Integer.parseInt(request.getParameter("stock"));
 
        Product p = new Product();
       
        p.setId(id);
        p.setCategory(c);
        p.setName(name);
        p.setSubTitle(subTitle);
        p.setOriginalPrice(originalPrice);
        p.setPromotePrice(promotePrice);
        p.setStock(stock);
        p.setCreateDate(new Date());
        
        productDAO.update(p);
		return "@admin_product_list?cid="+p.getCategory().getId();
	}

	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		request.setAttribute("p",p);
		return "admin/editProduct.jsp";
	}

	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDAO.get(cid);
         
        List<Product> ps = productDAO.list(cid, page.getStart(),page.getCount());
         
        int total = productDAO.getTotal(cid);
        page.setTotal(total);
        page.setParam("&cid="+c.getId());
         
        request.setAttribute("ps", ps);
        request.setAttribute("c", c);
        request.setAttribute("page", page);
         
        return "admin/listProduct.jsp";
	}
	
	public String editPropertyValue(HttpServletRequest request,HttpServletResponse response,Page page) {
		//获取产品id
		int id = Integer.parseInt(request.getParameter("id"));
		//获取产品对象
		Product p = productDAO.get(id);
		//先初始化属性（第一次访问的话，属性值是不存在的）
		propertyValueDAO.init(p);
		//通过pid获取属性值得集合
		List<PropertyValue> pvs = propertyValueDAO.list(id);
		//存到request域中
		request.setAttribute("p", p);
		request.setAttribute("pvs",pvs);
		return "admin/editPropertyValue.jsp";
	}
	
	public String updatePropertyUpdate(HttpServletRequest request,HttpServletResponse response,Page page) {
		int pvid = Integer.parseInt(request.getParameter("pvid"));
		String value = request.getParameter("value");
		
		PropertyValue pv = propertyValueDAO.get(pvid);
		pv.setValue(value);
		propertyValueDAO.update(pv);
		return "%success";
	}
}
