package com.core.gtd.common.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.sql.Update;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

//    @PrePersist
//    public void prePersist() {
//        createdAt = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
//
//        updatedAt = createdAt;
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        // 시:분:초를 00:00:00으로 초기화
//        updatedAt = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
//    }
}
