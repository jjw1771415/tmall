package tmall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.dao.ProductImageDAO;
import tmall.util.ImageUtil;
import tmall.util.Page;
@WebServlet("/productImageServlet")
public class ProductImageServlet extends BaseBackServlet{

	private static final long serialVersionUID = 1L;

	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String,String> params = new HashMap<String, String>();
		InputStream is = super.parseUpload(request, params);
		
		String type = params.get("type");
		int pid = Integer.parseInt(params.get("pid"));
		Product p = productDAO.get(pid);
		
		ProductImage pi = new ProductImage();
		pi.setProduct(p);
		pi.setType(type);
		productImageDAO.add(pi);
		
		//创建文件
		//上传的图片会有正常中等和小 三种格式
		String fileName = pi.getId()+".jpg";
		String imgFloder;
		String imgFloder_small = null;
		String imgFloder_middle = null;
		if(ProductImageDAO.type_single.equals(pi.getType())) {
			//获取路径
			imgFloder = request.getSession().getServletContext().getRealPath("img/productSingle");
			imgFloder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
			imgFloder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");
		}else {
			imgFloder = request.getSession().getServletContext().getRealPath("img/productDetail");
		}
		File f = new File(imgFloder,fileName);
		f.getParentFile().mkdirs();
		//复制文件
		try {
			if(is != null  && 0!=is.available())
			try(FileOutputStream fos = new FileOutputStream(f)){
				byte[] b = new byte[1024*1024];
				int length = 0;
				while((length = is.read(b))!=-1){
					fos.write(b, 0, length);
				}
				//刷新此输出流并强制任何缓冲的输出字节被写出
				fos.flush();
				//将文件转换为jpg格式保存到指定路径下
				BufferedImage img = ImageUtil.change2jpg(f);
				ImageIO.write(img, "jpg", f);
				//通过imageutil转换图片的大小
				if(ProductImageDAO.type_single.equals(pi.getType())) {
					File f_small = new File(imgFloder_small,fileName);
					File f_middle = new File(imgFloder_middle,fileName);
					f_small.getParentFile().mkdirs();
					f_middle.getParentFile().mkdirs();
					
					ImageUtil.resizeImage(f,56,56,f_small);
					ImageUtil.resizeImage(f,217,190,f_middle);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "@admin_productImage_list?pid="+p.getId();
	}

	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		ProductImage pi = productImageDAO.get(id);
		productImageDAO.delete(id);
		
		if(ProductImageDAO.type_single.equals(pi.getType())) {
			//获取路径
			String imgFloder = request.getSession().getServletContext().getRealPath("img/productSingle");
			String imgFloder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
			String imgFloder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");
			
			File f_single = new File(imgFloder,pi.getId()+".jpg");
			File f_small = new File(imgFloder_small,pi.getId()+".jpg");
			File f_middle = new File(imgFloder_middle,pi.getId()+".jpg");
			
			f_small.delete();
			f_middle.delete();
			f_single.delete();
		}else {
			String imgFloder = request.getSession().getServletContext().getRealPath("img/productDetail");
			File f_detail = new File(imgFloder,pi.getId()+".jpg");
			f_detail.delete();
		}
		return "@admin_productImage_list?pid=" + pi.getProduct().getId();
	}

	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}

	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}

	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		List<ProductImage> pis_single = productImageDAO.list(p,ProductImageDAO.type_single);
		List<ProductImage> pis_detail = productImageDAO.list(p,ProductImageDAO.type_detail);
		
		request.setAttribute("p",p);
		request.setAttribute("pis_single",pis_single);
		request.setAttribute("pis_detail",pis_detail);
		return "admin/listProductImage.jsp";
	}

}
