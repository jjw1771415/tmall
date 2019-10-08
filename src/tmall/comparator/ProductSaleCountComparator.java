package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class ProductSaleCountComparator implements Comparator<Product>{

	public int compare(Product o1, Product o2) {
		return o2.getSaleCount() - o1.getSaleCount();
	}

}
