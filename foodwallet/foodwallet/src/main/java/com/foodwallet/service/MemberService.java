package com.foodwallet.service;

import com.foodwallet.controller.response.MemberResponse;
import com.foodwallet.domain.Member;
import com.foodwallet.domain.Role;
import com.foodwallet.repository.MemberRepository;
import com.foodwallet.security.JwtTokenProvider;
import com.foodwallet.security.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public TokenInfo login(String email, String pwd) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, pwd);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> findMember = memberRepository.findByEmail(email);

        return findMember
            .map(this::createMemberUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    private UserDetails createMemberUserDetails(Member member) {
        return User.builder()
            .username(member.getEmail())
            .password(member.getPwd())
            .roles(member.getRole().toString())
            .build();
    }

    public MemberResponse join(String email, String pwd, String name, boolean isBusiness, String bank, String accountNumber) {
        Member member = Member.builder()
            .email(email)
            .pwd(passwordEncoder.encode(pwd))
            .name(name)
            .business(isBusiness)
            .role(isBusiness ? Role.ADMIN : Role.USER)
            .build();
        Member savedMember = memberRepository.save(member);
        return MemberResponse.of(savedMember);
    }
}
