package com.model2.mvc.web.purchase;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

//==> 구매관 Controller
@Controller
public class PurchaseController {

	//field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
		
	
	public PurchaseController() {
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addPurchase(@RequestParam("prod_no") int prodNo) throws Exception{
		
		System.out.println("/addPurchaseView.do");
	
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/addPurchaseView.jsp");
		
		modelAndView.addObject("product", productService.getProduct(prodNo));
			
		return modelAndView; 
	}
	
	@RequestMapping("/addPurchase.do")
	public ModelAndView addPurchaseView(@ModelAttribute("purchase") Purchase purchase, 
										@RequestParam("prodNo") int prodNo,
										@ModelAttribute("product") Product product,
										HttpSession session) throws Exception{
		
		System.out.println("/addPurchase.do");
		
		
		purchase.setBuyer((User)session.getAttribute("user"));
		purchase.setOrderDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		purchase.setPurchaseProd(product);
		purchase.setTranCode("1");
		
		purchaseService.addPurchase(purchase);		
		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/addPurchase.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/getPurchase.do")
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo) throws Exception{
		
		System.out.println("/addPurchaseView.do");
		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/getPurchase.jsp");
		
		modelAndView.addObject("purchase",purchaseService.getPurchaseByTranNo(tranNo));

		return modelAndView;
	}
	
	@RequestMapping("/listPurchase.do")
	public ModelAndView listPurchase( @ModelAttribute("search") Search search ,
									HttpSession session, 
									Model model )throws Exception{
		
		System.out.println("/listPurchase.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map= purchaseService.getPurchaseList( search , ((User)session.getAttribute("user")).getUserId() );
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/listPurchase.jsp");
		
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
				
		return modelAndView;
	}
	
	@RequestMapping("/listSale.do")
	public ModelAndView listSale(HttpServletRequest request) throws Exception{

		System.out.println("/listSale.do");
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/listSale.jsp");
		
		// Business logic 수행
		Search search = (Search)request.getAttribute("search");
		Map<String , Object> map = purchaseService.getSaleList(search);
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);

		// Model 과 View 연결
		System.out.println("ㅎㅎㅎ\n " + map);
		
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchase.do")
	public ModelAndView updateProduct(@RequestParam("tranNo") int tranNo,
								@ModelAttribute("purchase") Purchase purchase ) throws Exception{
		
		System.out.println("/updatePurchase.do");
		
		
		//Business Logic
		purchaseService.updatePurcahse(purchase);
		System.out.println("변경 후 : " +purchase);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/updatePurchase.jsp");
		modelAndView.addObject("purchase",purchase);
		
		return modelAndView;
	}

	@RequestMapping("/updatePurchaseView.do")
	public ModelAndView updateProductView( @RequestParam("tranNo") int tranNo, Model model) throws Exception{
		
		System.out.println("/updatePurchaseView.do");
		
		//Business Logic
		Purchase purchase = purchaseService.getPurchaseByTranNo(tranNo);

		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/updatePurchaseView.jsp");
		modelAndView.addObject("purchase",purchase);
		
		return modelAndView;
	}
	
	@RequestMapping("/updateTranCode.do")
	public ModelAndView updateTranCode(@RequestParam("tranCode") String tranCode,
							@RequestParam("tranNo") int tranNo) throws Exception{
		
		System.out.println("/updateTranCode.do");
		Purchase purchase =  purchaseService.getPurchaseByTranNo(tranNo);
		purchase.setTranCode(tranCode);
		purchaseService.updateTranCode(purchase);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView("/listPurchase.do");
				
		return modelAndView;
	}
	
	@RequestMapping("/updateTranCodeByProd.do")
	public ModelAndView updateTranCodeByProd(@RequestParam("tranCode") String tranCode,
									@RequestParam("prodNo") int prodNo) throws Exception{
		
		System.out.println("/updateTranCodeByProd.do");
		
		purchaseService.updateTranCode(purchaseService.getPurchaseByProdNo(prodNo));
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView("forward:/listProduct.do?menu=manage");
				
		return modelAndView;
	}
}
			




















