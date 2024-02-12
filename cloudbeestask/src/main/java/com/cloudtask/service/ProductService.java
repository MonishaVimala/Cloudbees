package com.cloudtask.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cloudtask.model.Product;
import com.cloudtask.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product products) {
        Product product = new Product();
        product.setName(products.getName());
        product.setDescription(products.getDescription());
        product.setPrice(products.getPrice());
        product.setQuantityAvailable(products.getQuantityAvailable());
        return productRepository.save(product);
    }
    
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }
    
    public Product updateProduct(Long productId, Product productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            // Update existing product with data from productDTO
            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setPrice(productDTO.getPrice());
            // Save and return the updated product
            return productRepository.save(existingProduct);
        } else {
            return null; // Product not found
        }
    }
    
    public boolean deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }
    
    public Product applyModification(Long productId, String modificationType, double modificationValue) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            double price = product.getPrice();
            if ("discount".equals(modificationType)) {
                price -= (price * modificationValue) / 100;
            } else if ("tax".equals(modificationType)) {
                price += (price * modificationValue) / 100;
            }
            product.setPrice(price);
            return productRepository.save(product);
        } else {
            return null; // Product not found
        }
    }
}
