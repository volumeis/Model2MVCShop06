package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;

//==> ��ǰ���� Controller
@Controller
public class ProductController {

	//field
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
		
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping("/addProduct.do")
	public String addProduct(@ModelAttribute("product") Product product) throws Exception {
		
		System.out.println("/addProduct.do");

		//tranCode set "0"
		//product.setProTranCode("0");
		product.setManuDate(product.getManuDate().replace("-", ""));
		//Business Logic 
		productService.addProduct(product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping("/getProduct.do")
	public String getProduct(@RequestParam("prodNo") int prodNo, @RequestParam("menu") String menu, 
								Model model) throws Exception{
		
		System.out.println("/getProduct.do");
		
		//Business Logic
		Product product = productService.getProduct(prodNo);

		// Model �� View ����
		model.addAttribute("product", product);
		
		
		if (menu.equals("manage")) {
			//menu = manage
			return "forward:/updateProductView.do?prodNo="+prodNo+"&menu=manage";
		} else if (menu.equals("ok")){
			//menu = ok
			return "forward:/product/confirmDetailProductView.jsp";
		} else {
			//menu = search
			return "forward:/product/purchaseProductView.jsp";
		} 
	}
	
	@RequestMapping("/listProduct.do")
	public String listProduct(@ModelAttribute("search") Search search,
								@RequestParam("menu") String menu,
								HttpSession session,  Model model) throws Exception {
		
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic ����
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		//���ǿ� ��� user���� Get
		User user = ((User)session.getAttribute("user"));
		String uri = "";
		System.out.println(user);
		if ( user == null || user.getRole().equals("user") ){
			
			System.out.println("--uri : /product/listProductUser.jsp ");
			uri = "/product/listProductUser.jsp";
		
		} else if (user.getRole().equals("admin")) {
		
			if(menu.equals("search")) {
				System.out.println("--uri : /product/listProductAdmin.jsp ");
				uri = "/product/listProductAdmin.jsp";
			} 
			else if(menu.equals("manage")){
				System.out.println("--uri : /listSale.do ");
				uri = "/listSale.do";
			}
		}
		
		return "forward:"+ uri;
	}
	
	@RequestMapping("/updateProduct.do")
	public String updateProduct(@RequestParam("prodNo") int prodNo,
								@ModelAttribute("product") Product product ) throws Exception{
		
		System.out.println("/updateProduct.do");
		
		//Business Logic
		productService.updateProduct(product);
		System.out.println("���� �� : " +product);
		
		return "redirect:/getProduct.do?prodNo="+prodNo+"&menu=ok";
	}

	@RequestMapping("/updateProductView.do")
	public String updateProductView( @RequestParam("prodNo") int prodNo, Model model) throws Exception{
		
		System.out.println("/updateProductView.do");
		
		//Business Logic
		Product product = productService.getProduct(prodNo);
		
		// Model �� View ����
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}
}
