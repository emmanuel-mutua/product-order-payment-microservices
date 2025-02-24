package com.emmutua.productService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private Long productId;
    private String productName;
    private String productDescription;
    private Long productQuantity;
    private Double productPrice;
    private String productImage;
    private String productCategory;
    private String productStatus;
    private LocalDateTime localDateTime;
}
