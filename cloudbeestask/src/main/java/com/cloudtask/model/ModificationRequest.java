package com.cloudtask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModificationRequest {
    private Long productId;
    private String modificationType;
    private double modificationValue;
}
