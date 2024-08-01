package com.cj.product.api.stock.service;

import com.cj.product.core.domain.product.Product;
import com.cj.product.core.domain.product.ProductInfo;
import com.cj.product.core.mapper.ProductObjectMapper;
import com.cj.product.core.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductStockApiService {

    private final ProductRepository productRepository;
    private final ProductObjectMapper productObjectMapper;

    /**
     * 상품 재고 조회.
     * @param productId
     * @return
     */
    public ProductInfo getProductStock(Long productId) {
        return productRepository.findById(productId)
                .map(productObjectMapper::toProductInfo)
                .orElse(null);
    }

    /**
     * 상품 재고 감소.
     * @param id
     * @param quantity
     */
    @Transactional
    public ProductInfo decreaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

        product.decreaseStock(quantity);
        Product savedProduct = productRepository.saveAndFlush(product);

        return productObjectMapper.toProductInfo(savedProduct);
    }

    /**
     * 상품 재고 증가.
     */
    @Transactional
    public ProductInfo increaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

        product.increaseStock(quantity);
        Product savedProduct = productRepository.saveAndFlush(product);

        return productObjectMapper.toProductInfo(savedProduct);
    }
}
