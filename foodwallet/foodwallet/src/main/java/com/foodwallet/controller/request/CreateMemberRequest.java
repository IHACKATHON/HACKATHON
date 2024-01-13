package com.foodwallet.controller.request;

import lombok.Getter;

@Getter
public class CreateMemberRequest {

    private String email;
    private String pwd;
    private String name;
    private String bank;
    private String accountNumber;
}
