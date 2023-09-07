package com.rocket.front.project.recruit.service;

import com.rocket.front.project.recruit.domain.request.AccessUserRequest;
import com.rocket.front.project.recruit.domain.response.RecruitCardResponse;
import com.rocket.front.project.recruit.domain.response.RecruitResponse;
import com.rocket.front.project.recruit.domain.response.RecruitTagResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Map;

@Service
public interface RecruitService {

    RecruitResponse getRecruit(Long recruitSeq);

    Map<String, String> validateHandling(BindingResult bindingResult);

    Page<RecruitCardResponse> getRecruitsList(Pageable pageable, Long type, String position, Long field, AccessUserRequest request);

    RecruitTagResponse getRecruitTagList();
}
