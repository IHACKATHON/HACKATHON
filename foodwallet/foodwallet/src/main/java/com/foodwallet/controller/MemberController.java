package com.foodwallet.controller;

import com.foodwallet.controller.request.CreateMemberRequest;
import com.foodwallet.controller.request.LoginRequest;
import com.foodwallet.controller.response.MemberResponse;
import com.foodwallet.security.TokenInfo;
import com.foodwallet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ApiResponse<TokenInfo> login(@RequestBody LoginRequest request) {

        TokenInfo tokenInfo = memberService.login(request.getEmail(), request.getPwd());

        return ApiResponse.ok(tokenInfo);
    }

    @PostMapping("/join")
    public ApiResponse<MemberResponse> join(@RequestBody CreateMemberRequest request) {
        MemberResponse response = memberService.join(request.getEmail(), request.getPwd(), request.getName(), false, request.getBank(), request.getAccountNumber());
        return ApiResponse.ok(response);
    }

    @PostMapping("/business-join")
    public ApiResponse<MemberResponse> businessJoin(@RequestBody CreateMemberRequest request) {
        MemberResponse response = memberService.join(request.getEmail(), request.getPwd(), request.getName(), true, request.getBank(), request.getAccountNumber());
        return ApiResponse.ok(response);
    }
}
