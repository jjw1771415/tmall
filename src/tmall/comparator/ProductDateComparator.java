package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class ProductDateComparator implements Comparator<Product>{

	public int compare(Product o1, Product o2) {
		return o1.getCreateDate().compareTo(o2.getCreateDate());
	}

}
