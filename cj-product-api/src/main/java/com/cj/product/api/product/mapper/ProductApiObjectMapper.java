package com.cj.product.api.product.mapper;

import com.cj.product.api.product.controller.dto.ProductStockInfoRes;
import com.cj.product.core.domain.product.ProductInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ProductApiObjectMapper {
    @Mapping(target = "productId", source = "id")
    ProductStockInfoRes toProductStockInfoRes(ProductInfo productInfo);
}
