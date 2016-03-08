package com.model2.mvc.service.product;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

//==> 물품관리에서 서비스할 내용 추상화/캡슐화한 Service  Interface Definition  
public interface ProductDao {
	
	//물품추가
	public void addProduct(Product product) throws Exception;
	
	//물품정보가져오기
	public Product getProduct(int productNo) throws Exception;

	//물품리스트가져오기
	public List<Product> getProductList(Search search) throws Exception;

	//물품정보수정
	public void updateProduct(Product product) throws Exception;
	
	// 게시판 Page 처리를 위한 전체Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception ;
}