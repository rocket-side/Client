package com.rocket.front.project.recruit.controller;

import com.rocket.front.project.recruit.domain.request.AccessUserRequest;
import com.rocket.front.project.recruit.domain.response.RecruitCardResponse;
import com.rocket.front.project.recruit.domain.response.RecruitResponse;
import com.rocket.front.project.recruit.domain.response.RecruitTagResponse;
import com.rocket.front.project.recruit.exception.RecruitNotFoundException;
import com.rocket.front.project.recruit.service.RecruitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import javax.validation.ValidationException;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/recruits")
public class RecruitController {

    private final RecruitService recruitService;

    /**
     * 공고글 목록 조회
     * @param model 모델 객체
     * @param pageable 페이지 관리를 위한 Pageable 객체
     * @param type 공고 타입
     * @param position 공고 포지션
     * @param field 공고 분야
     * @param request 사용자 액세스 요청
     * @param bindingResult 데이터 유효성 검사
     * @return 성공 시 공고글 목록 템플릿, 실패 시 에러 템플릿
     */
    @GetMapping
    public String getRecruitList(Model model, @PageableDefault ()Pageable pageable, Long type, String position, Long field, @Valid AccessUserRequest request, BindingResult bindingResult) {

        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("access-user", request);

                Map<String, String> validatorError = recruitService.validateHandling(bindingResult);
                for (String key : validatorError.keySet()) {
                    model.addAttribute(key, validatorError.get(key));
                }

                throw new ValidationException("유효성 검사에 실패하였습니다.");
            }

            Page<RecruitCardResponse> recruitList = recruitService.getRecruitsList(pageable, type, position, field, request);
            RecruitTagResponse recruitTagList = recruitService.getRecruitTagList();

            model.addAttribute("recruitList", recruitList);
            model.addAttribute("recruitTagList", recruitTagList);

            return "recruit/recruit-list";

        } catch (ValidationException e) {
            log.error("Validation failed: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());

            return "error";
        } catch (Exception e) {
            log.error("Error while processing request: {}", e.getMessage());
            return "error";
        }
    }

    /**
     * 해당 공고글 조회
     * @param recruitSeq 공고글 시퀀스
     * @param model 모델 객체
     * @return 공고글 조회 템플릿
     */
    @GetMapping("/{recruit-seq}")
    public String getRecruit(@PathVariable("recruit-seq") Long recruitSeq, Model model) {
        try {
            RecruitResponse recruitInfo = recruitService.getRecruit(recruitSeq);
            model.addAttribute("recruitInfo", recruitInfo);

            return "recruit/recruit-read";

        } catch (RecruitNotFoundException e) {
            log.error("Recruit found failed: {}", e.getMessage());
            model.addAttribute("errorMessage", "해당 공고를 찾을 수 없습니다.");

            return "error";
        } catch (Exception e) {
            log.error("Error while processing request: {}", e.getMessage());

            return "error";
        }
    }
}
