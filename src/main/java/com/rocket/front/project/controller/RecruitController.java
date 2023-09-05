package com.rocket.front.project.controller;

import com.rocket.front.project.adapter.RecruitAdapter;
import com.rocket.front.project.domain.recruit.response.RecruitCardResponse;
import com.rocket.front.project.domain.recruit.response.RecruitResponse;
import com.rocket.front.project.domain.recruit.response.RecruitTagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/recruits")
public class RecruitController {

    private final RecruitAdapter recruitAdapter;

    /**
     * 공고글 조회
     * @return 공고글 조회 템플릿
     */
    @GetMapping("/{recruit-seq}")
    public String getRecruit(@PathVariable("recruit-seq") String recruitSeq,
                             Model model) {
        try{
            RecruitResponse recruit = recruitAdapter.getRecruit(recruitSeq);
            model.addAttribute("recruit",recruit);
        }catch (Exception e) {
//            return "error";
            return "recruit/recruit-read";
        }

        return "recruit/recruit-read";
    }

    /**
     * 공고글 팀원 조회
     * @return 공고글 팀원 템플릿
     */
    @GetMapping("/{recruit-seq}/crews")
    public String getRecruitMember(@PathVariable("recruit-seq") String parameter) {
        return "recruit/project-member";
    }

    /**
     * 공고글 지원자 조회
     * @return 공고글 지원자 템플릿
     */
    @GetMapping("/{recruit-seq}/applicants")
    public String getRecruitApplicant(@PathVariable("recruit-seq") String parameter) {
        return "recruit/recruit-applicant";
    }

//    /**
//     * TODO 공고글 작성 없음
//     * @return 공고글 작성 템플릿
//     */
//    @GetMapping
//    public String getRecuritRegisterForm() {
//        return "recruit-"
//    }

    /**
     * 모든 공고글 조회
     * @return 공고글 목록 템플릿
     */
    @GetMapping
    public String getRecruitList(Model model,
                                 @PageableDefault()Pageable pageable) {
        try{
            Page<RecruitCardResponse> recruitList = recruitAdapter.getRecruitsList(pageable);
            RecruitTagResponse recruitTag = recruitAdapter.getRecruitTagList();
            model.addAttribute("recruitList",recruitList);
            model.addAttribute("recruitTagList",recruitTag);
        }catch (Exception e){
//            return "error";
            return "recruit/recruit-list";
        }
        return "recruit/recruit-list";
    }
}
