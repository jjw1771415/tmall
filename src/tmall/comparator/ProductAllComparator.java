package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;
//综合比较器，根据产品的销量和评价数，多的放前面
public class ProductAllComparator implements Comparator<Product>{

	public int compare(Product p1, Product p2) {
		return p2.getSaleCount()*p2.getReviewCount() - p1.getSaleCount()*p1.getReviewCount();
	}

}
