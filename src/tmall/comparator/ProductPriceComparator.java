package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;
//价格比较器，将价格低的放前面
public class ProductPriceComparator implements Comparator<Product>{

	public int compare(Product o1, Product o2) {
		return (int) (o1.getPromotePrice() - o2.getPromotePrice());
	}

}
