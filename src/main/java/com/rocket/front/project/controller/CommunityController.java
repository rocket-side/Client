package com.rocket.front.project.controller;

import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/community")
public class CommunityController {

    /**
     * 커뮤니티 게시글 조회
     * @param seq 게시글 시퀀스
     * @param page 댓글 페이지 번호
     * @return 커뮤니티 목록 템플릿
     */
    @GetMapping("/{post-seq}")
    public String getCommunity(@PathVariable("post-seq") Long seq, @PageableDefault Long page) {
        return "community/community-read";
    }

    /**
     * 커뮤니티 게시글 등록
     * @return 커뮤니티 게시글 작성 템플릿
     */
    @PostMapping("/register")
    public String getCommunityRegisterForm() {
        return "community/community-write";
    }

    /**
     * 커뮤니티 모든 게시글 조회
     * @param page 목록 페이지 번호
     * @param category 카테고리 번호? 이름?
     *                 TODO 번호면 Long 이름이면 String
     * @return 커뮤니티 게시글 템플릿
     */
    @GetMapping
    public String getCommunityList(@PageableDefault Long page, Long category) {
        return "community/community-list";
    }
}
