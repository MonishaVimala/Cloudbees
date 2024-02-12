package com.cloudtask.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudtask.model.ModificationRequest;
import com.cloudtask.model.Product;
import com.cloudtask.service.ProductService;

@RestController
@RequestMapping("/products")
public class CloudbeesController {
	Logger logger 
    = LoggerFactory.getLogger(CloudbeesController.class); 
	
	@Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        if (createdProduct != null) {
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/productById")
    public ResponseEntity<?> getProductById(@RequestBody Product productRequest) {
        logger.info("productId: " + productRequest.getProductId());
        Optional<Product> productOptional = productService.getProductById(productRequest.getProductId());
       
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.get());
        }else if(productOptional.isEmpty()) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product productRequest) {
        Product updatedProduct = productService.updateProduct(productRequest.getProductId(), productRequest);
        if (updatedProduct != null) {
            return ResponseEntity.ok("Product updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update product");
        }
    }
    
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        boolean deleted = productService.deleteProduct(productId);
        if (deleted) {
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
    
    @PostMapping("/applyModification")
    public ResponseEntity<?> applyModification(@RequestBody ModificationRequest request) {
        Long productId = request.getProductId();
        String modificationType = request.getModificationType();
        double modificationValue = request.getModificationValue();
        
        Product modifiedProduct = productService.applyModification(productId, modificationType, modificationValue);
        if (modifiedProduct != null) {
            return ResponseEntity.ok("Success: Product updated successfully. Details: " + modifiedProduct.toString());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failure: Product not found");
        }
    }

}
