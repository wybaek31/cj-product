package com.cj.product.api.product.service;

import com.cj.product.core.domain.product.Product;
import com.cj.product.core.domain.product.ProductInfo;
import com.cj.product.core.mapper.ProductObjectMapper;
import com.cj.product.core.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductApiService {

    private final ProductRepository productRepository;
    private final ProductObjectMapper productObjectMapper;

    /**
     * 상품 재고 조회.
     * @param productId
     * @return
     */
    public ProductInfo getProductStock(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        return productObjectMapper.toProductInfo(product);
    }

    /**
     * 상품 재고 감소.
     * @param id
     * @param quantity
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decreaseStock(Long id, int quantity) {
        Product product = productRepository.findById(id).orElseThrow();
        product.decreaseStock(quantity);
        productRepository.saveAndFlush(product);
    }
}
