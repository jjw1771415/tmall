package tmall.servlet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.util.HtmlUtils;

import tmall.bean.Category;
import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.bean.PropertyValue;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.comparator.ProductAllComparator;
import tmall.comparator.ProductDateComparator;
import tmall.comparator.ProductPriceComparator;
import tmall.comparator.ProductReviewComparator;
import tmall.comparator.ProductSaleCountComparator;
import tmall.dao.OrderDAO;
import tmall.dao.OrderItemDAO;
import tmall.dao.ProductImageDAO;
import tmall.util.Page;
@WebServlet("/foreServlet")
public class ForeServlet extends BaseForeServlet{

	private static final long serialVersionUID = 1L;

	public String home(HttpServletRequest request,HttpServletResponse response,Page page) {
		List<Category> cs = categoryDAO.list();
		//填充产品集合
		productDAO.fill(cs);
		//填充分类的每行数据
		productDAO.fillByRow(cs);
		//保存到request域中
		request.setAttribute("cs",cs);
		//转发到home.jsp
		return "home.jsp";
	}
	
	public String register(HttpServletRequest request,HttpServletResponse response,Page page) {
		String name = request.getParameter("name");
		name = HtmlUtils.htmlEscape(name);
		String password = request.getParameter("password");
		boolean flag = userDAO.isExist(name);
		if(flag) {
			request.setAttribute("msg","用户名已存在，请更改用户名");
			return "register.jsp";
		}
		
		User bean = new User();
		bean.setName(name);
		bean.setPassword(password);
		userDAO.add(bean);
		return "registerSuccess.jsp";
	}
	
	public String login(HttpServletRequest request,HttpServletResponse response,Page page) {
		String name = request.getParameter("name");
		name = HtmlUtils.htmlEscape(name);
		String password = request.getParameter("password");
		
		User user = userDAO.get(name, password);
		
		if(user == null) {
			request.setAttribute("msg","账户或密码错误");
			return "login.jsp";
		}
		request.getSession().setAttribute("user",user);
		return "fore_home";
	}
	
	public String logout(HttpServletRequest request,HttpServletResponse response,Page page) {
		request.getSession().removeAttribute("user");
		return "fore_home";
	}
	
	public String product(HttpServletRequest request,HttpServletResponse response,Page page) {
		int id = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(id);
		List<ProductImage> pis_single = productImageDAO.list(p,ProductImageDAO.type_single);
		List<ProductImage> pis_detail = productImageDAO.list(p,ProductImageDAO.type_detail);
		List<PropertyValue> ptvs = propertyValueDAO.list(p.getId());
		//获取评价集合
		List<Review> rs = reviewDAO.list(p.getId());
		
		int saleCount = orderItemDAO.getSaleCount(p.getId());
		int reviewCount = reviewDAO.getCount(id);
		
		p.setProductSingleImages(pis_single);
		p.setProductDetailImages(pis_detail);
		p.setSaleCount(saleCount);
		p.setReviewCount(reviewCount);
		
		request.setAttribute("p",p);
		request.setAttribute("ptvs",ptvs);
		request.setAttribute("rs",rs);
		
		return "product.jsp";
	}
	
	public String checkLogin(HttpServletRequest request,HttpServletResponse response,Page page) {
		User user = (User) request.getSession().getAttribute("user");
		if(user != null) {
			return "%success";
		}
		return "%fail";
	}
	
	public String loginAjax(HttpServletRequest request,HttpServletResponse response,Page page) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		User user = userDAO.get(name, password);
		
		if(user != null) {
			request.getSession().setAttribute("user",user);
			return "%success";
		}
		return "%fail";
	}
	
	public String category(HttpServletRequest request,HttpServletResponse response,Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		
		//获取分类对象，并填充产品,并且为产品设置销量和评价数量
		Category c = categoryDAO.get(cid);
		productDAO.fill(c);
		productDAO.setSaleAndReviewNumber(c.getProducts());
		
		//获取参数sort
		String sort = request.getParameter("sort");
		if(sort != null) {
			switch (sort) {
			case "review":
				Collections.sort(c.getProducts(),new ProductReviewComparator());
				break;
			case "date":
				Collections.sort(c.getProducts(),new ProductDateComparator());
				break;
			case "saleCount":
				Collections.sort(c.getProducts(),new ProductSaleCountComparator());
				break;
			case "price":
				Collections.sort(c.getProducts(),new ProductPriceComparator());
				break;
			case "all":
				Collections.sort(c.getProducts(),new ProductAllComparator());
				break;
			}
		}
		request.setAttribute("c",c);
		return "category.jsp";
	}
	
	public String search(HttpServletRequest request,HttpServletResponse response,Page page) {
		String keyword = request.getParameter("keyword");
		List<Product> ps = productDAO.search(keyword,0, 20);
		productDAO.setSaleAndReviewNumber(ps);
		request.setAttribute("ps",ps);
		return "searchResult.jsp";
	}
	
	public String buyone(HttpServletRequest request,HttpServletResponse response,Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		int num = Integer.parseInt(request.getParameter("num"));
		Product p = productDAO.get(pid);
		User user = (User) request.getSession().getAttribute("user");
		//订单项的id
		int oiid = 0;
		boolean flag = false;
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		for (OrderItem oi : ois) {
			//该用户已存在该产品的订单项，则增加数量
			if(oi.getProduct().getId() == p.getId()) {
				oi.setNumber(oi.getNumber() + num);
				orderItemDAO.update(oi);
				flag = true;
				oiid = oi.getId();
				break;
			}
		}
		if(!flag) {
			OrderItem oi = new OrderItem();
			oi.setUser(user);
			oi.setNumber(num);
			oi.setProduct(p);
			orderItemDAO.add(oi);
			oiid = oi.getId();
		}
		return "@fore_buy?oiid="+oiid;
	}
	
	public String buy(HttpServletRequest request,HttpServletResponse response,Page page) {
		String[] oiids = request.getParameterValues("oiid");
		List<OrderItem> ois = new ArrayList<OrderItem>();
		int total = 0;
		for (String s :oiids) {
			int oiid = Integer.parseInt(s);
			OrderItem oi = orderItemDAO.get(oiid);
			total += oi.getProduct().getPromotePrice()*oi.getNumber();
			ois.add(oi);
		}
		request.getSession().setAttribute("ois",ois);
		request.setAttribute("total",total);
		return "buy.jsp";
	}
	
	public String addCart(HttpServletRequest request,HttpServletResponse response,Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		int num = Integer.parseInt(request.getParameter("num"));
		Product p = productDAO.get(pid);
		User user = (User) request.getSession().getAttribute("user");
		
		boolean flag = false;
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		for (OrderItem oi : ois) {
			if(oi.getProduct().getId() == p.getId()) {
				num += oi.getNumber();
				oi.setNumber(num);
				orderItemDAO.update(oi);
				flag = true;
				break;
			}
		}
		if(!flag) {
			OrderItem oi = new OrderItem();
			oi.setNumber(num);
			oi.setProduct(p);
			oi.setUser(user);
			orderItemDAO.add(oi);
		}
		return "%success";
	}
	
	//查看购物车
	public String cart(HttpServletRequest request,HttpServletResponse response,Page page) {
		//1.从session中获取user
		User user = (User) request.getSession().getAttribute("user");
		//2.通过uid获取相关订单项
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		//3.存到request域中
		request.setAttribute("ois",ois);
		return "cart.jsp";
	}
	
	//提交订单前的改变数量
	public String changeOrderItem(HttpServletRequest request,HttpServletResponse response,Page page) {
		User user = (User) request.getSession().getAttribute("user");
		if(user==null) {
			return "%fail";
		}
		
		int pid = Integer.parseInt(request.getParameter("pid"));
		int number = Integer.parseInt(request.getParameter("number"));
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		for (OrderItem oi : ois) {
			if(oi.getProduct().getId() == pid) {
				oi.setNumber(number);
				orderItemDAO.update(oi);
				break;
			}
		}
		return "%success";
	}
	
	//删除订单项
	public String deleteOrderItem(HttpServletRequest request,HttpServletResponse response,Page page) {
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			return "%fail";
		}
		int oiid = Integer.parseInt(request.getParameter("oiid"));
		orderItemDAO.delete(oiid);
		return "%success";
	}
	
	public String createOrder(HttpServletRequest request,HttpServletResponse response,Page page) {
		//1.获取user
		User user = (User) request.getSession().getAttribute("user");
		//从session中获取订单项集合
		@SuppressWarnings("unchecked")
		List<OrderItem> ois = (List<OrderItem>) request.getSession().getAttribute("ois");
		if(ois == null) {
			return "@login.jsp";
		}
		//2.获取订单信息
		String address = request.getParameter("address");
		String post = request.getParameter("post");
		String receiver = request.getParameter("receiver");
		String mobile = request.getParameter("mobile");
		String userMessage = request.getParameter("userMessage");
		//3.生成订单号
		String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(1000);
		//4.封装一个订单对象
		Order o = new Order();
		o.setAddress(address);
		o.setOrderCode(orderCode);
		o.setPost(post);
		o.setReceiver(receiver);
		o.setMobile(mobile);
		o.setUserMessage(userMessage);
		o.setCreateDate(new Date());
		o.setUser(user);
		//5.把订单状态设置为未支付
		o.setStatus(OrderDAO.waitPay);
		
		orderDAO.add(o);
		
		float total = 0;
		//6.遍历订单项，为每个订单项设置订单id
		for (OrderItem oi : ois) {
			oi.setOrder(o);
			//7.更新数据库
			orderItemDAO.update(oi);
			//8.统计总金额
			total += oi.getNumber()*oi.getProduct().getPromotePrice();
		}
		//9.客户端跳转到待支付页面，带上订单id和金额
		return "@fore_alipay?oid="+o.getId()+"&total="+total;
	}
	
	public String alipay(HttpServletRequest request,HttpServletResponse response,Page page) {
		return "alipay.jsp";
	}
	
	public String payed(HttpServletRequest request,HttpServletResponse response,Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		o.setPayDate(new Date());
		o.setStatus(OrderDAO.waitDelivery);
		orderDAO.update(o);
		request.setAttribute("o",o);
		return "payed.jsp";
	}
	
	public String bought(HttpServletRequest request,HttpServletResponse response,Page page) {
		User user = (User) request.getSession().getAttribute("user");
		List<Order> os = orderDAO.list(user.getId(),OrderDAO.delete);
		orderItemDAO.fill(os);
		request.setAttribute("os", os);
		return "bought.jsp";
	}
	//确认付款
	public String confirmPay(HttpServletRequest request,HttpServletResponse response,Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		orderItemDAO.fill(o);
		request.setAttribute("o",o);
		return "confirmPay.jsp";
	}
	//确认收货
	public String orderConfirmed(HttpServletRequest request,HttpServletResponse response,Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		o.setStatus(OrderDAO.waitReview);
		o.setConfirmDate(new Date());
		orderDAO.update(o);
		return "orderConfirmed.jsp";
	}
	
	public String deleteOrder(HttpServletRequest request,HttpServletResponse response,Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		o.setStatus(OrderDAO.delete);
		orderDAO.update(o);
		return "%success";
	}
	
	public String review(HttpServletRequest request,HttpServletResponse response,Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		Product p = productDAO.get(pid);
		//获取产品的评价集合
		List<Review> reviews = reviewDAO.list(pid);
		productDAO.setSaleAndReviewNumber(p);
		request.setAttribute("p",p);
		request.setAttribute("o", o);
		request.setAttribute("reviews",reviews);
		return "review.jsp";
	}
	
	public String submitReview(HttpServletRequest request,HttpServletResponse response,Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		o.setStatus(OrderDAO.finish);
		orderDAO.update(o);
		
		Product p = productDAO.get(pid);
		String content = request.getParameter("content");
		content = HtmlUtils.htmlEscape(content);
		
		User user = (User) request.getSession().getAttribute("user");
		
		Review r = new Review();
		r.setContent(content);
		r.setCreateDate(new Date());
		r.setProduct(p);
		r.setUser(user);
		
		reviewDAO.add(r);
		
		return "@fore_review?oid="+oid+"&showonly=true"+"&pid="+pid;
		
	}
}
