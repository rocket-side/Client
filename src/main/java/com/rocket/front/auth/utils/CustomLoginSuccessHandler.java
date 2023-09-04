//package com.rocket.front.auth.utils;
//
//import com.rocket.front.auth.repository.MemberRepository;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@Slf4j
//@AllArgsConstructor
//@RequiredArgsConstructor
//public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
////    private final MemberRepository memberRepository;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        // TODO 통신해서 memberSeq 가져와야 함
//        // TODO 로그인 성공 시 Session, Cookie 등을 설정
//        // TODO Redis 추가
//        HttpSession session = request.getSession();
//        // TODO memberSeq 가져와야 함
////        session.setAttribute("memberSeq",memberRepository.findByEmail(authentication.getName()));
//        session.setAttribute("email",authentication.getName());
//
//        response.addCookie(new Cookie("SESSION", session.getId()));
//    }
//}
