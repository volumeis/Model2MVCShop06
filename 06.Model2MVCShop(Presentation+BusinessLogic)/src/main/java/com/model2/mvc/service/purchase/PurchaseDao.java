package com.model2.mvc.service.purchase;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

//==> 구매리에서 CRUD 추상화/캡슐화한 DAO Interface Definition
public interface PurchaseDao {

	public void addPurchase(Purchase purchase) throws Exception;
	
	public Purchase getPurchaseByTranNo(int tranNo) throws Exception;
	
	public Purchase getPurchaseByProdNo(int prodNo) throws Exception;
	
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception;
	
	public List<Purchase> getSaleList(Search search) throws Exception;
	
	public void updatePurcahse(Purchase purchase) throws Exception;
	
	public void updateTranCode(Purchase purchase) throws Exception;
	
	public int getTotalCount(Search search) throws Exception;
	
}