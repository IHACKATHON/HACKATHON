package com.foodwallet.controller.response;

import com.foodwallet.domain.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreResponse {

    private Long storeId;
    private String name;
    private String address;
    private String category;
    private String opening;
    private String image;

    @Builder
    private StoreResponse(Long storeId, String name, String address, String category, String opening, String image) {
        this.storeId = storeId;
        this.name = name;
        this.address = address;
        this.category = category;
        this.opening = opening;
        this.image = image;
    }

    public static StoreResponse of(Store store) {
        return StoreResponse.builder()
            .storeId(store.getId())
            .name(store.getName())
            .address(store.getAddress())
            .category(store.getCategory())
            .opening(store.getOpening())
            .image(store.getImage().getStoreFileName())
            .build();
    }
}
