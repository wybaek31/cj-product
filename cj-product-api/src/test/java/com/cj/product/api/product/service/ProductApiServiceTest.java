package com.cj.product.api.product.service;

import com.cj.product.core.domain.product.Product;
import com.cj.product.core.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@SpringBootTest
class ProductApiServiceTest {

    @Autowired
    private ProductApiService productApiService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void insert() {
        Product product = new Product(1L, "상품 1", 100, "wooyoung");
        productRepository.saveAndFlush(product);
    }

    @AfterEach
    public void delete() {
        productRepository.deleteAll();
    }

    @Test
    public void decrease_test() {
        // given
        productApiService.decreaseStock(1L, 1);

        // when
        Product product = productRepository.findById(1L).orElseThrow();

        // then
        assertEquals(99, product.getQuantity());
    }

}