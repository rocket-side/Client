package com.rocket.front.auth.service;

import com.rocket.front.auth.domain.response.MemberLoginProcResponseDto;
import com.rocket.front.auth.utils.OAuthAttributes;
import com.rocket.front.auth.entity.Member;
import com.rocket.front.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        OAuth2AccessToken oAuth2AccessToken = userRequest.getAccessToken();

        // 네이버인지 카카오인지 구글인지....구분해주는
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // TODO 이거 왜필요함????
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId,userNameAttributeName,oAuth2User.getAttributes(),userRequest.getAccessToken().getTokenValue());
        log.info("::::email:::: {}",attributes.getAttributes());
        log.info("::::pk:::: {}",attributes.getNameAttributeKey());
        log.info("::::access_token:::: {}",userRequest.getAccessToken().getTokenValue());
        log.info("::::access_token:::: {}",userRequest.getAccessToken().getTokenType().getValue());



        log.info("::::oAuth2User {}", (Object) oAuth2User.getAttribute("access_token"));

        MemberLoginProcResponseDto member = getUser(attributes);


        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole()))
                ,attributes.getAttributes()
                , attributes.getNameAttributeKey()
        );


//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority(RoleType.USER.getCode()))
//                ,attributes.getAttributes()
//                , attributes.getNameAttributeKey()
//        );
    }

    // TODO restTemplate 변경해야 함
    private MemberLoginProcResponseDto getUser(OAuthAttributes attributes) {
        log.info("getUser>>>>");
        // TODO RestTemplate Adapter 소환해야 함
        Member member =  memberRepository.findByEmail(attributes.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("userEmail not found"));

        return convertToMemberLoginProcResponseDto(member);
    }

    private MemberLoginProcResponseDto convertToMemberLoginProcResponseDto(Member member) {
        return MemberLoginProcResponseDto.builder()
                .memberSeq(member.getMemberSeq())
                .email(member.getEmail())
                .role(member.getAuthority().getName())
                .build();
    }


}
