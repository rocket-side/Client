package com.rocket.front.project.position.service;

import com.rocket.front.project.position.adapter.PositionAdapter;
import com.rocket.front.project.position.domain.response.ApplicantsResponse;
import com.rocket.front.project.position.domain.response.ApplyStatusResponse;
import com.rocket.front.project.position.domain.response.RecruitCrewResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionAdapter positionAdapter;

    @Override
    public List<ApplyStatusResponse> getApplicantStatusList(Long recruitSeq) {
        return positionAdapter.getApplicantStatusList(recruitSeq);
    }

    @Override
    public List<ApplicantsResponse> getApplicantsList(Long recruitSeq) {
        return positionAdapter.getApplicantsList(recruitSeq);
    }

    @Override
    public List<RecruitCrewResponse> getRecruitCrewList(Long recruitSeq) {
        return positionAdapter.getRecruitCrewList(recruitSeq);
    }
}
