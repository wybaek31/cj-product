package com.cj.product.api.product.controller;

import com.cj.product.api.product.facade.ProductApiFacade;
import com.cj.product.core.domain.product.Product;
import com.cj.product.core.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/api/stock")
@Tag(name = "상품 재고 API")
public class ProductApiController {

    private final ProductApiFacade productApiFacade;

    @Operation(summary = "상품의 재고 조회 API",
            description = "상품의 재고 조회 API 입니다. 호출시 상품 ID를 확인해주세요." +
                    "\n- "
    )
    @GetMapping(value = "/v1/{productId}", produces = "application/json")
    public CommonResponse<Product> getProductStock(@PathVariable("productId") Long productId) {
        return CommonResponse.success(productApiFacade.getProductStock(productId));
    }
}
