package com.foodwallet.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String pwd;
    private String name;
    private boolean business;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private Member(String email, String pwd, String name, boolean business, Role role) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.business = business;
        this.role = role;
    }
}
