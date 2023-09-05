package com.rocket.front.auth.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocket.front.auth.domain.response.MemberLoginInfoResponseDto;
import com.rocket.front.member.adapter.MemberAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberAdapter memberAdapter;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        MemberLoginInfoResponseDto userInfo = memberAdapter.getMemberInfoByEmail(user.getUsername());

        try {
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(userInfo));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // TODO Redis 추가
        HttpSession session = request.getSession();

        session.setAttribute("memberSeq",userInfo.getMemberSeq());
        session.setAttribute("name",userInfo.getNickname());

        response.addCookie(new Cookie("SESSION", UUID.randomUUID().toString()));

        response.sendRedirect("/recruits");
    }
}