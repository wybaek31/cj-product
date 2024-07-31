package com.cj.product.core.domain.product;

import com.cj.product.core.domain.common.CommonEntity;
import com.cj.product.core.exception.BaseException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.cj.product.core.response.ErrorCode.ERROR_OUT_OF_STOCK;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CJ_PRODUCT")
public class Product extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "REG_USER")
    private String regUser;

    @Column(name = "UPD_USER")
    private String updUser;

    public Product(Long productId, String productName, int quantity, String regUser) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.regUser = regUser;
        this.updUser = regUser;
    }

    /**
     * 재고 차감.
     * @param quantity
     */
    public void decreaseStock(int quantity) {
        if (this.quantity - quantity < 0) {
            throw new BaseException(ERROR_OUT_OF_STOCK);
        }
        this.quantity -= quantity;
    }

}