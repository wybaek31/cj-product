package com.cj.product.core.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CommonEntity {

    @CreatedDate
    @Comment("등록일시")
    @Column(name = "REG_DATE", updatable = false, nullable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Comment("수정일시")
    @Column(name = "UPD_DATE", nullable = false)
    private LocalDateTime updDate;
}
