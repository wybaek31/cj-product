package com.cj.product.core.domain.product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductInfo {
    private Long id;
    private String productName;
    private int quantity;
    private String regUser;
    private LocalDateTime regDate;
    private String updUser;
    private LocalDateTime updDate;
}
