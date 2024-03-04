package com.core.gtd.src.users.entity;

import com.core.gtd.src.basket.entity.Task;
import com.core.gtd.src.common.constatnt.Role;
import com.core.gtd.src.common.entity.BaseEntity;
import com.core.gtd.src.users.model.request.UserJoinRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"user\"")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String name;
    private String email;
    private String address;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Task> task = new ArrayList<>();

    // TODO : 연관관계 객체 정의 및 관계 설정
//    @OneToMany(mappedBy = "Users")
//    private List<Task> task;

    @PrePersist
    private void setCreatedAt() {
        createdAt = LocalDateTime.now();
    }
}
