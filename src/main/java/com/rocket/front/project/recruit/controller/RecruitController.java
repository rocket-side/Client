package com.rocket.front.project.recruit.controller;

import com.rocket.front.project.recruit.adapter.RecruitAdapter;
import com.rocket.front.project.recruit.domain.request.AccessUserRequest;
import com.rocket.front.project.recruit.domain.response.RecruitCardResponse;
import com.rocket.front.project.recruit.domain.response.RecruitResponse;
import com.rocket.front.project.recruit.domain.response.RecruitTagResponse;
import com.rocket.front.project.recruit.service.RecruitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/recruits")
public class RecruitController {

    private final RecruitAdapter recruitAdapter;

    private final RecruitService recruitService;

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
     * @param model
     * @param pageable
     * @param type
     * @param position
     * @param field
     * @param request
     * @param bindingResult
     * @return
     */
    @GetMapping
    public String getRecruitList(Model model,
                                 @PageableDefault()Pageable pageable, Long type, String position, Long field, @Valid AccessUserRequest request, BindingResult bindingResult) {
        try {

            if(bindingResult.hasErrors()) {
                model.addAttribute("access-user", request);

                Map<String, String> validatorError = recruitService.validateHandling(bindingResult);
                for(String key : validatorError.keySet()) {
                    model.addAttribute(key, validatorError.get(key));
                }

                return "error";
            }

            Page<RecruitCardResponse> recruitList = recruitService.getRecruitsList(pageable, type, position, field, request);
            RecruitTagResponse recruitTag = recruitService.getRecruitTagList();
            model.addAttribute("recruitList",recruitList);
            model.addAttribute("recruitTagList",recruitTag);

        } catch (Exception e){
//            return "error";
            return "recruit/recruit-list";
        }
        return "recruit/recruit-list";
    }
}
