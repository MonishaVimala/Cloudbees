package com.cloudtask.cloudbeestask;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cloudtask.model.Product;
import com.cloudtask.repository.ProductRepository;
import com.cloudtask.service.ProductService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class CloudbeestaskApplicationTests {
	
	@Autowired
    ProductRepository productRepo;
	
	@Autowired
	ProductService productSer;


	@Test
	@Order(1)
	public void testCreate() {
		Product p = new Product();
//		p.setProductId(null);
		p.setName("Mirrors");
		p.setDescription("Good");
		p.setPrice(200);
		p.setQuantityAvailable(100);
		productRepo.save(p);
		assertNotNull(productRepo.findById(1L).get());
	}
	
	@Test
	@Order(2)
	public void testReadAll() {
		List<Product> list = productRepo.findAll();
		assertThat(list).size().isGreaterThan(0);
	}
	
	@Test
	@Order(3)
	public void testSingleProduct() {
		Product product = productRepo.findById(1L).get();
		assertEquals(200.00, product.getPrice());
	}
	
	@Test
	@Order(4)
	public void testUpdate() {
		Product p = productRepo.findById(1L).get();
		p.setPrice(100.00);
		productRepo.save(p);
		assertNotEquals(200.00, productRepo.findById(1L).get().getPrice());
	}
	
	@Test
	@Order(5)
	public void testApplyModification_Discount() {
        // Arrange
        Long productId = 1L;
        double initialPrice = 100.0;
        double discount = 10.0;
        Product product = new Product();
        product.setProductId(productId);
        product.setPrice(initialPrice);
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        when(productRepo.save(product)).thenReturn(product);

        // Act
        Product modifiedProduct = productSer.applyModification(productId, "discount", discount);

        // Assert
        assertThat(modifiedProduct).isNotNull();
        assertEquals(productId, modifiedProduct.getProductId());
        assertEquals(90.0, modifiedProduct.getPrice());
    }
	
	@Test
    @Order(6)
    public void testApplyModification_Tax() {
        // Arrange
        Long productId = 1L;
        double initialPrice = 100.0;
        double tax = 10.0;
        Product product = new Product();
        product.setProductId(productId);
        product.setPrice(initialPrice);
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        when(productRepo.save(product)).thenReturn(product);

        // Act
        Product modifiedProduct = productSer.applyModification(productId, "tax", tax);

        // Assert
        assertThat(modifiedProduct).isNotNull();
        assertEquals(productId, modifiedProduct.getProductId());
        assertEquals(99.0, modifiedProduct.getPrice());
    }
	
	@Test
	@Order(6)
	public void testDelete() {
		productRepo.deleteById(1L);
		assertThat(productRepo.existsById(1L)).isFalse();
	}
	
	

}
