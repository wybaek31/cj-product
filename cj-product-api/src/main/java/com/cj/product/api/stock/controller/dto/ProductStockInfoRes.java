package com.cj.product.api.stock.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStockInfoRes {
    private Long productId;
    private String productName;
    private int quantity;
}
