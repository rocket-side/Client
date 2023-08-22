package com.rocket.front.service;

//import com.jeongm.rocketsecurity.entity.Member;
//import com.jeongm.rocketsecurity.repository.MemberRepository;
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

    // TODO 통신 후 받아올 수 있어야 함
//    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Member member = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException(email + "not found"));
//        log.info(String.valueOf(member.getMemberSeq()));
//        return new User( member.getEmail()
//        ,member.getPassword()
//        , Collections.singleton(new SimpleGrantedAuthority(member.getAuthority().getName())));

        return new User( "admin"
                ,"1234"
                , Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
