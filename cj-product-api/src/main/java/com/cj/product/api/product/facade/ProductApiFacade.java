package com.cj.product.api.product.facade;

import com.cj.product.api.product.controller.dto.ProductStockInfoRes;
import com.cj.product.api.product.mapper.ProductApiObjectMapper;
import com.cj.product.api.product.service.ProductApiService;
import com.cj.product.core.domain.product.ProductInfo;
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
    private final ProductApiObjectMapper productApiObjectMapper;

    /**
     * 상품 재고 조회.
     * @param productId
     * @return
     */
    public ProductStockInfoRes getProductStock(Long productId) {
        ProductInfo productInfo = productApiService.getProductStock(productId);
        return productApiObjectMapper.toProductStockInfoRes(productInfo);
    }

    /**
     * 상품 재고 차감.
     * @param productId
     * @param quantity
     */
    public void decreaseStock(Long productId, int quantity) {
        // Step 1. 상품 유효성 검사.
        ProductInfo productInfo = productApiService.getProductStock(productId);
        if (productInfo == null) {
            throw new RuntimeException("상품 정보가 없습니다.");
        }

        if (productInfo.getQuantity() < quantity) {
            throw new RuntimeException("재고가 부족합니다.");
        }

        // Step 2. 상품 재고 차감.
        RLock lock = redissonClient.getLock("PRODUCT:STOCK:" + productId);
        try {
            boolean isLocked = lock.tryLock(10, 5, TimeUnit.SECONDS);

            if (!isLocked) {
                throw new RuntimeException("락 실패");
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
