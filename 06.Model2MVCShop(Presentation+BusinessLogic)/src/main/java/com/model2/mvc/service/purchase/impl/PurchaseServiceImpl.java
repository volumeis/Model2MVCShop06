package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;
	
	public PurchaseServiceImpl() {
		System.out.println(this.getClass());
	}
	public void setPurchaseServiceImpl(PurchaseDao purchaseDao){
		this.purchaseDao = purchaseDao;
	}
	
	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDao.addPurchase(purchase);
	}
	
	@Override
	public Purchase getPurchaseByTranNo(int tranNo) throws Exception {
		return purchaseDao.getPurchaseByTranNo(tranNo);
	}
	@Override
	public Purchase getPurchaseByProdNo(int prodNo) throws Exception {
		return purchaseDao.getPurchaseByProdNo(prodNo);
	}
	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		List<Purchase> list = purchaseDao.getPurchaseList(search, buyerId);
		int totalCount = list.size();
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount",totalCount);
		
		return map;
	}
	@Override
	public Map<String, Object> getSaleList(Search search) throws Exception {
		List<Purchase> list = purchaseDao.getSaleList(search);
		int totalCount = purchaseDao.getTotalCount(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount",totalCount);
		
		return map;
	}
	@Override
	public void updatePurcahse(Purchase purchase) throws Exception {
		purchaseDao.updatePurcahse(purchase);
	}
	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDao.updateTranCode(purchase);	
	}
	


}
