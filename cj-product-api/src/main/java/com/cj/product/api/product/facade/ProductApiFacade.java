package com.cj.product.api.product.facade;

import com.cj.product.api.product.service.ProductApiService;
import com.cj.product.core.domain.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProductApiFacade {

    private final ProductApiService productApiService;
    private final RedissonClient redissonClient;

    public Product getProductStock(Long productId) {
        return productApiService.getProductStock(productId);
    }

    public void decreaseStock(Long productId, int quantity) {
        RLock lock = redissonClient.getLock(productApiService.toString());

        try {
            boolean available = lock.tryLock(10, 5, TimeUnit.SECONDS);

            if (!available) {
                System.out.println("lock 획득 실패");
                return;
            }

            productApiService.decreaseStock(productId, quantity);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Lock interrupted", e);
        } finally {
            lock.unlock();
        }
    }
}
