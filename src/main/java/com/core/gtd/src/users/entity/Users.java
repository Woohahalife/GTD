package com.core.gtd.src.users.entity;

import com.core.gtd.common.constatnt.Role;
import com.core.gtd.common.entity.BaseEntity;
import com.core.gtd.src.basket.entity.Task;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@Entity
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
@SQLRestriction("state <> 'DELETE'")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"user\"")
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String password;
    private String name;
    private String email;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    // TODO : 연관관계 객체 정의 및 관계 설정
//    @OneToMany(mappedBy = "Users")
//    private List<Task> task;



}
