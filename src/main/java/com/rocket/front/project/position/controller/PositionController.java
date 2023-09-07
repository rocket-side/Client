package com.rocket.front.project.position.controller;

import com.rocket.front.project.position.domain.response.ApplicantsResponse;
import com.rocket.front.project.position.domain.response.ApplyStatusResponse;
import com.rocket.front.project.position.domain.response.RecruitCrewResponse;
import com.rocket.front.project.position.service.PositionService;
import com.rocket.front.project.recruit.domain.response.RecruitResponse;
import com.rocket.front.project.recruit.exception.RecruitNotFoundException;
import com.rocket.front.project.recruit.service.RecruitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/recruits")
public class PositionController {

    private final PositionService positionService;

    private final RecruitService recruitService;

    /**
     * 공고글 모집 현황 정보 조회
     * @param recruitSeq 공고글 시퀀스
     * @param model 모델 객체
     * @return 성공 시 공고글 모집 현황 조회 템플릿, 실패 시 에러 템플릿
     */
    @GetMapping("/{recruit-seq}/applyStatus")
    public String getApplicantStatueList(@PathVariable("recruit-seq") Long recruitSeq, Model model) {
        try {
            List<ApplyStatusResponse> applyStatusResponseList = positionService.getApplicantStatusList(recruitSeq);
            model.addAttribute("applyStatusList", applyStatusResponseList);

            return "recruit/recruit-applicant";
        } catch (RecruitNotFoundException e) {
            log.error("Recruit found failed: {}", e.getMessage());
            model.addAttribute("errorMessage", "해당 공고를 찾을 수 없습니다.");

            return "error";
        } catch (Exception e) {
            log.error("Error while processing request: {}", e.getMessage());

            return "error";
        }
    }

    /**
     * 공고글 지원자 조회
     * @param recruitSeq 공고글 시퀀스
     * @param model 모델 객체
     * @return 공고글 지원자 조회 템플릿
     */
    @GetMapping("/{recruit-seq}/applicants")
    public String getRecruitApplicant(@PathVariable("recruit-seq") Long recruitSeq, Model model) {
        try {
            List<ApplicantsResponse> applicantsResponseList = positionService.getApplicantsList(recruitSeq);
            model.addAttribute("applicantsList", applicantsResponseList);

            return "recruit/recruit-applicant";
        } catch (RecruitNotFoundException e) {
            log.error("Recruit found failed: {}", e.getMessage());
            model.addAttribute("errorMessage", "해당 공고를 찾을 수 없습니다.");

            return "error";
        } catch (Exception e) {
            log.error("Error while processing request: {}", e.getMessage());

            return "error";
        }
    }

    /**
     * 공고글 팀원 조회
     * @param recruitSeq 공고글 시퀀스
     * @param model 모델 객체
     * @return 공고글 팀원 조회 템플릿
     */
    @GetMapping("/{recruit-seq}/crews")
    public String getRecruitMember(@PathVariable("recruit-seq") Long recruitSeq, Model model) {
        try {
            List<RecruitCrewResponse> recruitCrewResponseList = positionService.getRecruitCrewList(recruitSeq);
            model.addAttribute("recruitCrewList", recruitCrewResponseList);

            RecruitResponse recruitInfo = recruitService.getRecruit(recruitSeq);
            model.addAttribute("recruitInfo", recruitInfo);

            List<ApplyStatusResponse> applyStatusResponseList = positionService.getApplicantStatusList(recruitSeq);
            model.addAttribute("applyStatusList", applyStatusResponseList);

            return "recruit/project-member";
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
