package com.foodwallet.controller.request;

import lombok.Getter;

@Getter
public class CreateStoreRequest {

    private Long memberId;
    private String name;
    private String info;
    private String address;
    private String opening;
    private String category;
}
