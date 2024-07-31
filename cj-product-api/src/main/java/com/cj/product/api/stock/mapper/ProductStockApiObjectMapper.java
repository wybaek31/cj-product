package com.cj.product.api.stock.mapper;

import com.cj.product.api.stock.controller.dto.ProductStockInfoRes;
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
public interface ProductStockApiObjectMapper {
    @Mapping(target = "productId", source = "productId")
    ProductStockInfoRes toProductStockInfoRes(ProductInfo productInfo);
}
