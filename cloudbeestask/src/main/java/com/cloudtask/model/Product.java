package com.cloudtask.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private String description;
    private double price;
    private int quantityAvailable;
    
    
 // Method to apply discount or tax
    public void applyModification(String modificationType, double modificationValue) {
        if ("discount".equals(modificationType)) {
            this.price -= (this.price * modificationValue) / 100;
        } else if ("tax".equals(modificationType)) {
            this.price += (this.price * modificationValue) / 100;
        }
    }
    
}