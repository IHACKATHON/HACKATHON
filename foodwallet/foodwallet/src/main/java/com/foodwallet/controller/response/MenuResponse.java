package com.foodwallet.controller.response;

import com.foodwallet.domain.Menu;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuResponse {

    private Long menuId;
    private String name;
    private int price;
    private String menuImage;

    @Builder
    private MenuResponse(Long menuId, String name, int price, String menuImage) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.menuImage = menuImage;
    }

    public static MenuResponse of(Menu menu) {
        return MenuResponse.builder()
            .menuId(menu.getId())
            .name(menu.getMenu())
            .price(menu.getPrice())
            .menuImage(menu.getImage().getStoreFileName())
            .build();
    }
}
