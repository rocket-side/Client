package com.rocket.front.project.introduction.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocket.front.project.introduction.adapter.IntroductionAdapter;
import com.rocket.front.project.introduction.domain.response.CommentResponse;
import com.rocket.front.project.introduction.domain.response.IntroductionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/introductions")
public class IntroductionController {
    private final IntroductionAdapter introductionAdapter;

    /**
     * 해당 소개글 조회
     * @return 소개글 조회 템플릿
     */
    @GetMapping("/{recruit-seq}")
    public String getIntroduction(@PathVariable("recruit-seq") String recruitSeq, HttpSession httpSession, Model model) {

        IntroductionResponse introduction = introductionAdapter.getIntroduction(recruitSeq);
        List<CommentResponse> comments = introductionAdapter.getIntroductionComments(recruitSeq);
        String memberSeq = null;
        if(Objects.nonNull(httpSession.getAttribute("memberSeq"))){
            memberSeq = httpSession.getAttribute("memberSeq").toString();
        }

        Boolean accessUser = introductionAdapter.isIntroductionWriter(recruitSeq,memberSeq);

        model.addAttribute("introduction",introduction);
        model.addAttribute("comments",comments);
        model.addAttribute("isWriter",accessUser);

//        try {
//            IntroductionResponse introduction = introductionAdapter.getIntroduction(recruitSeq);
//            List<CommentResponse> comments = introductionAdapter.getIntroductionComments(recruitSeq);
//            Boolean accessUser = introductionAdapter.isIntroductionWriter(recruitSeq,httpSession.getAttribute("memberSeq").toString());
//
//            model.addAttribute("introduction",introduction);
//            model.addAttribute("comments",comments);
//            model.addAttribute("isWriter",accessUser);
//        }catch (Exception e) {
////            return "error";
//            return "introduction/introduction-read";
//        }

        return "introduction/introduction-read";
    }

    /**
     * 소개글 작성
     * @return 소개글 작성 템플릿
     */
    @GetMapping("/register")
    public String getIntroductionRegisterForm(Model model) {

        return "introduction/introduction-write";
    }

    /**
     * 모든 소개글 조회
     * @return 소개글 목록 템플릿
     */
    @GetMapping
    public String getIntroductionList(Model model) {
        return "introduction/introduction-list";
    }
}
