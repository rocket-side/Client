package com.rocket.front.auth.config;

//import com.rocket.front.auth.utils.CustomLoginSuccessHandler;
import com.rocket.front.auth.utils.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                    .antMatchers("/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
    //                    .antMatchers("/rocket/recurite/update/**").access("@projectUserService.checkPermission(requset)")
//                .antMatchers("/fonts/**","/img/**").permitAll()
                .antMatchers("/login","/signup").anonymous()
                    .antMatchers("/").permitAll()
//                    .anyRequest().authenticated()
                    .anyRequest().permitAll()
                .and()
                // TODO Spring Security JWT Filter Load
//                .addFilterBefore()
                // TODO Session 기반의 인증기반을 사용하지 않고 추후 JWT를 이용하여서 인증 예정
//                .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                // TODO 토큰을 사용하면 csrf를 disable해야한다고 함 -> 알아볼 것
                .csrf()
                    .disable()
                .formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginPage("/login")
//                    .successHandler(customLoginSuccessHandler())
                .and()
                .oauth2Login()
                    .clientRegistrationRepository(clientRegistrationRepository())
                    .authorizedClientService(oAuth2AuthorizedClientService())
//                    .successHandler(customLoginSuccessHandler())
                    .loginPage("/login")
//                    .failureUrl("/") // TODO 로그인 실패 시 회원가입 페이지로 리다이렉트
//                    .redirectionEndpoint()
                .and()
                .logout() // logout 이후 세션 전체 삭제 여부
                    .invalidateHttpSession(true)
                .and()
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest
                        .toStaticResources()
                        .atCommonLocations()
                )
                .antMatchers("/img/**","/fonts/**")
                .antMatchers("/h2-console/**");
    }



//    @Bean
//    public CustomLoginSuccessHandler customLoginSuccessHandler() {
//        return new CustomLoginSuccessHandler();
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }


    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = new ArrayList<>();
        registrations.add(github());
        registrations.add(google());
        registrations.add(naver());
        registrations.add(kakao());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    private ClientRegistration github() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .clientId("7dedde138c5b33876fb1")
                .clientSecret("63e2ffcc209bf731021584e52155abef8950740d")
                .tokenUri("https://github.com/login/oauth/access_token")
                .scope("name", "user:email","profile")
//                .userNameAttributeName("id")
                .build();
    }

    private ClientRegistration google() {
        return CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId("869923208998-tin7kf2932oj3je1ttjsf80s86m9a4dr.apps.googleusercontent.com")
                .clientSecret("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")
                .scope("email","profile") // email, profileimg 가져옴
                .userNameAttributeName("email")
                .build();
    }

    // TODO clientId properties로 해서 보안 챙기기

    private ClientRegistration naver() {

        return ClientRegistration.withRegistrationId("naver")
                .clientId("cSofoRH1ofaDsbaZYmOf")
                .clientSecret("4N3xnZg3dy")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .scope("nickname", "email", "profile_image","name")
                .userNameAttributeName("response")
                .build();
    }

    private ClientRegistration kakao() {
        return ClientRegistration.withRegistrationId("kakao")
                .clientId("2d667b0ce5f78b1066b16736ccf427b6")
                .clientSecret("tJuycjIevMRm7nCg1F2L1khU5hYGrIKf")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
                .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}")
                .tokenUri("https://kauth.kakao.com/oauth/token")
                .userInfoUri("https://kapi.kakao.com/v2/user/me")
                .scope("profile_nickname", "account_email", "profile_image")
                .userNameAttributeName("id")
                .build();
    }

}
