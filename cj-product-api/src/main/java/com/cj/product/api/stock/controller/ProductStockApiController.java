package com.cj.product.api.stock.controller;

import com.cj.product.api.stock.controller.dto.ProductStockInfoRes;
import com.cj.product.api.stock.controller.dto.ProductStockUpdateReq;
import com.cj.product.api.stock.facade.ProductStockApiFacade;
import com.cj.product.core.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/api/stock")
@Tag(name = "상품 재고 API")
public class ProductStockApiController {

    private final ProductStockApiFacade productStockApiFacade;

    @Operation(summary = "상품의 재고 조회 API",
            description = "상품의 재고 조회 API 입니다. 호출시 상품 ID를 확인해주세요." +
                    "\n- "
    )
    @GetMapping(value = "/v1/{productId}", produces = "application/json")
    public CommonResponse<ProductStockInfoRes> getProductStock(@PathVariable("productId") Long productId) {
        return CommonResponse.success(productStockApiFacade.getProductStock(productId));
    }

    @Operation(summary = "상품의 재고 차감 API",
            description = "상품의 재고를 차감하는 API 입니다. 호출시 상품ID와 차감할 수량을 확인해 주세요.")
    @PatchMapping(value = "/v1/{productId}/stock", produces = "application/json")
    public CommonResponse<String> decreaseStock(@PathVariable("productId") Long productId,
                                                @RequestBody ProductStockUpdateReq request) {
        productStockApiFacade.decreaseStock(productId, request.getQuantity());
        return CommonResponse.success("Success");
    }

}
