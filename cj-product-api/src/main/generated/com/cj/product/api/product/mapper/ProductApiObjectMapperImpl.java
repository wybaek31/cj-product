package com.cj.product.api.product.mapper;

import com.cj.product.api.product.controller.dto.ProductStockInfoRes;
import com.cj.product.core.domain.product.ProductInfo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-30T22:52:05+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class ProductApiObjectMapperImpl implements ProductApiObjectMapper {

    @Override
    public ProductStockInfoRes toProductStockInfoRes(ProductInfo productInfo) {
        if ( productInfo == null ) {
            return null;
        }

        ProductStockInfoRes.ProductStockInfoResBuilder productStockInfoRes = ProductStockInfoRes.builder();

        productStockInfoRes.productId( productInfo.getId() );
        productStockInfoRes.productName( productInfo.getProductName() );
        productStockInfoRes.quantity( productInfo.getQuantity() );

        return productStockInfoRes.build();
    }
}
