package com.rocket.front.auth.service;

import com.rocket.front.member.adapter.MemberAdapter;
import com.rocket.front.member.domain.response.MemberLoginInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberAdapter memberAdapter;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        MemberLoginInfoResponseDto member = getUser(email);

        log.info("::::member   {}::::",member.getEmail());

//        return .builder()
//                .userSeq(member.getMemberSeq())
//                .email(member.getEmail())
//                .password(member.getPassword())
//                .username(member.getNickname())
//                .roles(Collections.singleton(new SimpleGrantedAuthority(member.getRoleResponseDto().getName())))
//                .build();

        return new User(member.getEmail()
        , member.getPassword()
        , Collections.singleton(new SimpleGrantedAuthority(member.getRoleResponseDto().getName())));
    }

    private MemberLoginInfoResponseDto getUser(String email) {
        return memberAdapter.getSignInMemberInfoByEmail(email);
    }

}
