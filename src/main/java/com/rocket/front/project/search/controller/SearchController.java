package com.rocket.front.project.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController {

    /**
     * 검색 결과 조회
     * @return 검색 결과 목록 템플릿
     */
    @GetMapping
    public String getSearch() {
        return "search-result";
    }
}
