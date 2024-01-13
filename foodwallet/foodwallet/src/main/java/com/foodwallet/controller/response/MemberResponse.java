package com.foodwallet.controller.response;

import com.foodwallet.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {

    private Long memberId;
    private String name;
    private String email;

    @Builder
    public MemberResponse(Long memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
            .memberId(member.getId())
            .name(member.getName())
            .email(member.getEmail())
            .build();
    }
}
