package com.rocket.front.auth.service;

import com.rocket.front.auth.domain.response.MemberLoginProcResponseDto;
import com.rocket.front.auth.entity.Member;
import com.rocket.front.auth.repository.MemberRepository;
import com.rocket.front.auth.utils.OAuthAttributes;
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

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberLoginProcResponseDto member = getUser(email);

        return new User(member.getEmail()
        ,member.getPassword()
        , Collections.singleton(new SimpleGrantedAuthority(member.getRole())));
    }

    // TODO restTemplate 변경해야 함
    private MemberLoginProcResponseDto getUser(String email) {
        // TODO RestTemplate Adapter 소환해야 함
        Member member =  memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("userEmail not found"));

        return convertToMemberLoginProcResponseDto(member);
    }

    private MemberLoginProcResponseDto convertToMemberLoginProcResponseDto(Member member) {
        return MemberLoginProcResponseDto.builder()
                .memberSeq(member.getMemberSeq())
                .email(member.getEmail())
                .password(member.getPassword())
                .role(member.getAuthority().getName())
                .build();
    }
}
