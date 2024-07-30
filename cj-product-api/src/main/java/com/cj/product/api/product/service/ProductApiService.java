package com.cj.product.api.product.service;

import com.cj.product.core.domain.product.Product;
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

    public Product getProductStock(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    /**
     * 재고 감소
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
