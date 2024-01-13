package com.foodwallet.controller.response;

import com.foodwallet.domain.Store;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StoreDetailResponse {

    private Long storeId;
    private String name;
    private String address;
    private String category;
    private String opening;
    private String storeImage;
    private List<MenuResponse> menus = new ArrayList<>();

    @Builder
    private StoreDetailResponse(Long storeId, String name, String address, String category, String opening, String storeImage, List<MenuResponse> menus) {
        this.storeId = storeId;
        this.name = name;
        this.address = address;
        this.category = category;
        this.opening = opening;
        this.storeImage = storeImage;
        this.menus = menus;
    }

    public static StoreDetailResponse of(Store store) {
        return StoreDetailResponse.builder()
            .storeId(store.getId())
            .name(store.getName())
            .address(store.getAddress())
            .category(store.getCategory())
            .opening(store.getOpening())
            .storeImage(store.getImage().getStoreFileName())
            .menus(store.getMenus().stream()
                .map(MenuResponse::of)
                .collect(Collectors.toList())
            )
            .build();
    }
}
