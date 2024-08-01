package com.cj.product.api.stock.service;

import com.cj.product.core.domain.product.Product;
import com.cj.product.core.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@SpringBootTest
class ProductStockApiServiceTest {

    @Autowired
    private ProductStockApiService productStockApiService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void insert() {
        Product product = new Product(1L, "상품 1", 100, 5,"wooyoung");
        productRepository.saveAndFlush(product);
    }

    @AfterEach
    public void delete() {
        productRepository.deleteAll();
    }

    @Test
    public void decrease_test() {
        // given
        productStockApiService.decreaseStock(1L, 1);

        // when
        Product product = productRepository.findById(1L).orElseThrow();

        // then
        assertEquals(99, product.getQuantity());
    }

}