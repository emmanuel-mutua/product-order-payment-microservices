package com.emmutua.productService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String productName;
    private String productDescription;
    private Long productQuantity;
    private Double productPrice;
    private String productImage;
    private String productCategory;
}
