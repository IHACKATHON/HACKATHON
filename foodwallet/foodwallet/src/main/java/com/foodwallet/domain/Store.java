package com.foodwallet.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;
    private String name;
    private String info;
    private String address;
    private String opening;
    @Embedded
    private UploadFile image;
    private boolean open;
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "store")
    private List<Menu> menus = new ArrayList<>();

    @Builder
    private Store(String name, String info, String address, String opening, UploadFile image, boolean open, String category, Member member, List<Menu> menus) {
        this.name = name;
        this.info = info;
        this.address = address;
        this.opening = opening;
        this.image = new UploadFile();
        this.open = true;
        this.category = category;
        this.member = member;
        this.menus = new ArrayList<>();
    }

    public static Store create(String name, String info, String address, String opening, String category, Member member) {
        return Store.builder()
            .name(name)
            .info(info)
            .address(address)
            .opening(opening)
            .category(category)
            .member(member)
            .build();
    }
}
