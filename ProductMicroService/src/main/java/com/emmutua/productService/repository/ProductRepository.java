package com.emmutua.productService.repository;

import com.emmutua.productService.entity.Product;
import com.emmutua.productService.model.ProductProjection;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends R2dbcRepository<Product, Long> {
    @Query("""
            SELECT * FROM product where product_id = :productId
            """)
    Mono<ProductProjection> getProductProjection(@Param("productId") Long productId);
}
