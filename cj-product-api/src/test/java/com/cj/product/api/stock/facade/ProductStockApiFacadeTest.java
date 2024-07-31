package com.cj.product.api.stock.facade;

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
class ProductStockApiFacadeTest {

    @Autowired
    private ProductStockApiFacade productStockApiFacade;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void before() {
        Product product = new Product(1L, "상품 1", 100, "wooyoung");
        productRepository.save(product);
    }

    @AfterEach
    public void after() {
        productRepository.deleteAll();
    }

    @Test
    public void 재고_동시성_테스트() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    productStockApiFacade.decreaseStock(1L, 1);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Product product = productRepository.findById(1L).orElseThrow();

        assertEquals(0, product.getQuantity());
    }

}