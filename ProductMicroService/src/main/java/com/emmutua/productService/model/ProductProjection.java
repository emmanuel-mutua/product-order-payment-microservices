package com.emmutua.productService.model;

public interface ProductProjection {
    String getProductId();
    String getProductName();
    String getProductDescription();
    Long getProductQuantity();
    Double getProductPrice();
    String getProductImage();
    String getProductCategory();
}
