package com.rocket.front.project.position.service;

import com.rocket.front.project.position.domain.response.ApplicantsResponse;
import com.rocket.front.project.position.domain.response.ApplyStatusResponse;
import com.rocket.front.project.position.domain.response.RecruitCrewResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PositionService {

    List<ApplyStatusResponse> getApplicantStatusList(Long recruitSeq);

    List<ApplicantsResponse> getApplicantsList(Long recruitSeq);

    List<RecruitCrewResponse> getRecruitCrewList(Long recruitSeq);
}
