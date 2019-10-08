package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;
//评价比较器
public class ProductReviewComparator implements Comparator<Product>{

	public int compare(Product p1, Product p2) {
		return p2.getReviewCount() - p1.getReviewCount();
	}

}
