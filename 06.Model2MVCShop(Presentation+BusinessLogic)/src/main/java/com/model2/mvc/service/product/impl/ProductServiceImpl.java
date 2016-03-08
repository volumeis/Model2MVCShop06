package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;

//==>물품관리 서비스 구현
@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {

	/// Field
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDao;
	
	public ProductServiceImpl() {
		System.out.println(this.getClass());
	}
	public void setProductServiceImpl(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	@Override
	public void addProduct(Product product) throws Exception {
		productDao.addProduct(product);		
	}

	@Override
	public Product getProduct(int productNo) throws Exception {
		return productDao.getProduct(productNo);
	}

	@Override
	public Map<String, Object> getProductList(Search search) throws Exception {
		List<Product> list = productDao.getProductList(search);
		int totalCount = productDao.getTotalCount(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount",totalCount);
		
		return map;
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		productDao.updateProduct(product);
	}

}
