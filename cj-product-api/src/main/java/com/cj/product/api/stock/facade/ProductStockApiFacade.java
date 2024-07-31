package com.cj.product.api.stock.facade;

import com.cj.product.api.stock.controller.dto.ProductStockInfoRes;
import com.cj.product.api.stock.mapper.ProductStockApiObjectMapper;
import com.cj.product.api.stock.service.ProductStockApiService;
import com.cj.product.core.domain.product.ProductInfo;
import com.cj.product.core.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.cj.product.core.constants.CoreConstants.REDIS_LOCK_PRODUCT_KEY;
import static com.cj.product.core.response.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProductStockApiFacade {

    private final ProductStockApiService productStockApiService;
    private final RedissonClient redissonClient;
    private final ProductStockApiObjectMapper productStockApiObjectMapper;

    /**
     * 상품 재고 조회.
     * @param productId
     * @return
     */
    public ProductStockInfoRes getProductStock(Long productId) {
        ProductInfo productInfo = productStockApiService.getProductStock(productId);
        return productStockApiObjectMapper.toProductStockInfoRes(productInfo);
    }

    /**
     * 상품 재고 차감.
     * @param productId
     * @param quantity
     */
    public void decreaseStock(Long productId, int quantity) {
        // Step 1. 상품 유효성 검사.
        ProductInfo productInfo = productStockApiService.getProductStock(productId);
        if (productInfo == null) {
            throw new BaseException(ERROR_PRODUCT_NOT_FOUND);
        }

        if (productInfo.getQuantity() < quantity) {
            throw new BaseException(ERROR_OUT_OF_STOCK);
        }

        // Step 2. 상품 재고 차감 Lock.
        RLock lock = redissonClient.getLock(REDIS_LOCK_PRODUCT_KEY + productId);
        try {
            boolean isLocked = lock.tryLock(10, 5, TimeUnit.SECONDS);

            if (!isLocked) {
                throw new BaseException(COMMON_REDIS_LOCK_ERROR);
            }
            // Step 3. 상품 재고 차감.
            productStockApiService.decreaseStock(productId, quantity);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Lock interrupted", e);
        } finally {
            lock.unlock();
        }
    }
}
