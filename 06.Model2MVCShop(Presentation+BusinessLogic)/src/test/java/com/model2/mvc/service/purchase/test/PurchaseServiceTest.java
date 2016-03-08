package com.model2.mvc.service.purchase.test;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;

/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class PurchaseServiceTest {

	// ==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	
	//@Test
	public void testAddUser() throws Exception {

		Purchase purchase = new Purchase();
		
		User user = new User();
		user.setUserId("user12");
		
		Product product = new Product();
		product.setProdNo(10080);
		
		//purchase.setTranNo(9999);
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setPaymentOption("1");
		purchase.setReceiverName("12a34");
		purchase.setReceiverPhone("asdf");
		purchase.setDlvyAddr("qqwe");
		purchase.setDlvyRequest("zzxc");
		purchase.setTranCode("2");
		purchase.setOrderDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2010-12-21").getTime() ));
		//purchase.setDlvyDate("2100-12-12");
		//System.out.println(""+ purchase.getBuyer() + ": " + purchase.getPurchaseProd());
		
		System.out.println(purchase);
		purchaseService.addPurchase(purchase);

		//purchase = purchaseService.getPurchaseByTranNo(10066);


		// ==> console 확인
		// System.out.println(user);

		// ==> API 확인
		//Assert.assertEquals("2010-12-21", purchase.getOrderDate());
		//Assert.assertEquals("2100-12-12", purchase.getDlvyDate());
	}
	
	//@Test
	public void getPurchaseByTranNo() throws Exception {

		Purchase pchase = new Purchase();
		// ==> 필요하다면...
		// user.setUserId("testUserId");
		// user.setUserName("testUserName");
		// user.setPassword("testPasswd");
		// user.setSsn("1111112222222");
		// user.setPhone("111-2222-3333");
		// user.setAddr("경기도");
		// user.setEmail("test@test.com");
		//pchase.setTranNo(10066);
		//pchase = purchaseService.getPurchaseByTranNo(10006);

		// ==> console 확인
		// System.out.println(user);
		pchase = purchaseService.getPurchaseByTranNo(10066);
		System.out.println(pchase);
		// ==> API 확인
		Assert.assertEquals(10066, pchase.getTranNo());
	}
	
	//@Test
	public void getPurchaseByProdNo() throws Exception {

		Purchase pchase = new Purchase();
		// ==> 필요하다면...
		// user.setUserId("testUserId");
		// user.setUserName("testUserName");
		// user.setPassword("testPasswd");
		// user.setSsn("1111112222222");
		// user.setPhone("111-2222-3333");
		// user.setAddr("경기도");
		// user.setEmail("test@test.com");
		//pchase.setTranNo(10066);
		//pchase = purchaseService.getPurchaseByTranNo(10006);

		// ==> console 확인
		// System.out.println(user);
		pchase = purchaseService.getPurchaseByProdNo(10120);
		System.out.println(pchase);
		// ==> API 확인
		Assert.assertEquals(10083, pchase.getTranNo());
	}
	
	@Test
	public void testUpdatePurchase() throws Exception{
		Purchase purchase = new Purchase();
		purchase = purchaseService.getPurchaseByTranNo(10083);
		purchase.setPaymentOption("1");
		purchase.setReceiverName("t2");
		purchase.setReceiverPhone("t3");
		purchase.setDlvyAddr("t4");
		purchase.setDlvyRequest("t5");
		purchase.setDlvyDate("2016-03-03");
		
		purchaseService.updatePurcahse(purchase);
		Purchase newPurchase = new Purchase();
		newPurchase = purchaseService.getPurchaseByTranNo(10083);
		
		Assert.assertEquals(10083, newPurchase.getTranNo());
		Assert.assertEquals("2016-03-03",newPurchase.getDlvyDate() );
		/*
		 * 	payment_option	= #{paymentOption:VARCHAR},
	   		receiver_name	= #{receiverName:VARCHAR}, 
	   		receiver_phone	= #{receiverPhone:VARCHAR}, 
	   		dlvy_addr		= #{dlvyAddr:VARCHAR}, 
	   		dlvy_request	= #{dlvyRequest:VARCHAR},  
	   		dlvy_date		= #{dlvyDate:DATE}
		 */
	}
	
	//@Test
	public void updateTranCode() throws Exception {

		Purchase purchase = new Purchase();
		purchase = purchaseService.getPurchaseByTranNo(10083);
		//Assert.assertEquals("2  ",purchase.getTranCode());
		System.out.println(purchase.getTranCode());
		purchase.setTranCode("1");		
		purchaseService.updateTranCode(purchase);
		purchase = purchaseService.getPurchaseByTranNo(10083);
		Assert.assertEquals("1  ",purchase.getTranCode());
	}
	
	//@Test
	public void getPurchaseList() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		Map<String, Object> map = purchaseService.getPurchaseList(search, "user02");

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		System.out.println("1list : " + list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("10002");
		map = purchaseService.getPurchaseList(search, "user02");

		list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}
	
	//@Test
	public void testGetSaleList() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(15);
		search.setSearchCondition("0");
		search.setSearchKeyword("10002");
		Map<String, Object> map = purchaseService.getSaleList(search);
		
		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(1, list.size());

		// ==> console 확인
		System.out.println("%%%%%");		 System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("===========2============================");

		search.setSearchCondition("1");
		search.setSearchKeyword("123");
		map =  purchaseService.getSaleList(search);

		list = (List<Object>) map.get("list");
		//Assert.assertEquals(0, list.size());
		// ==> console 확인
		System.out.println("%%%%%");
		System.out.println(list);
		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}
	
	/*
	@Test
	public void testGetUserListByUserName() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("1");
		search.setSearchKeyword("SCOTT");
		Map<String, Object> map = userService.getUserList(search);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setSearchCondition("1");
		search.setSearchKeyword("" + System.currentTimeMillis());
		map = userService.getUserList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(0, list.size());

		// ==> console 확인
		System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}
	*/
}