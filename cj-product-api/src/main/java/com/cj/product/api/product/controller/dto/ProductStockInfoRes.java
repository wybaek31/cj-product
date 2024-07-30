package com.cj.product.api.product.controller.dto;

import lombok.Builder;

@Builder
public class ProductStockInfoRes {
    private Long productId;
    private String productName;
    private int quantity;
}
