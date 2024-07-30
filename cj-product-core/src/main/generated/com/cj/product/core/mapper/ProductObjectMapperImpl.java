package com.cj.product.core.mapper;

import com.cj.product.core.domain.product.Product;
import com.cj.product.core.domain.product.ProductInfo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-30T22:51:30+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class ProductObjectMapperImpl implements ProductObjectMapper {

    @Override
    public ProductInfo toProductInfo(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductInfo productInfo = new ProductInfo();

        productInfo.setId( product.getId() );
        productInfo.setProductName( product.getProductName() );
        productInfo.setQuantity( product.getQuantity() );
        productInfo.setRegUser( product.getRegUser() );
        productInfo.setRegDate( product.getRegDate() );
        productInfo.setUpdUser( product.getUpdUser() );
        productInfo.setUpdDate( product.getUpdDate() );

        return productInfo;
    }
}
