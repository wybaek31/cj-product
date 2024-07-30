package com.cj.product.api.product.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStockReq {
    private Long productId;
    private int quantity;
}
