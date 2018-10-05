package com.capgemini.productapp;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestBody;

import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest {

	@Mock
	ProductService productService;

	@InjectMocks
	ProductController productController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

	}

	@Test
	public void testProductWhichAddsProduct() throws Exception {
		Product product = new Product(1, "Laptop", "Electronics", 10000);
		 when(productService.addProduct(Mockito.isA(Product.class))).thenReturn(product);
		mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON_UTF8).content(
				"{\"productId\":\"1\",\"productName\":\"Laptop\",\"productCategory\":\"Electronics\",\"productPrice\":\"10000\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.productId").exists())
				.andDo(print())
				.andExpect(jsonPath("$.productId").value(1))
				.andExpect(status().isOk()) ;
				

	}
	
	
	@Test
	public void testProductWhichUpdatesProduct() throws Exception{
		Product updatedProduct = new Product(1, "Laptop", "Electronics", 20000);
		when(productService.findProductById(updatedProduct.getProductId())).thenReturn(updatedProduct);
		when(productService.updateProduct(Mockito.isA(Product.class))).thenReturn(updatedProduct);
		mockMvc.perform(
				put("/product").contentType(MediaType.APPLICATION_JSON_UTF8)
		        .content("{ \"productId\": \"1\", \"productName\" : \"Laptop\", \"productCategory\" : \"Electronics\",\"productPrice\" : \"20000\" }")
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").exists()) 
				.andExpect(jsonPath("$.productPrice").value(20000))
				.andDo(print()) ;
				
	}
	
	@Test
	public void testProductWhichDeletesProduct() throws Exception{
		Product product = new Product(1, "Laptop", "Electronics", 20000);
		when(productService.findProductById(product.getProductId())).thenReturn(product);
		mockMvc.perform(
				delete("/products/{productId}",product.getProductId()))
				.andExpect(status().isOk())
				.andDo(print()) ;
			verify(productService, times(1)).deleteProduct(product);
		
}
	
	@Test
	public void testProductWhichFindsProduct() throws Exception{
		Product product = new Product(1, "Laptop", "Electronics", 20000);
		when(productService.findProductById(product.getProductId())).thenReturn(product);
		mockMvc.perform(
				get("/products/{productId}",product.getProductId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").exists()) 
				.andExpect(jsonPath("$.productPrice").value(20000))
				.andDo(print()) ;
			
		
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}