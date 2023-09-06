package com.rocket.front.project.recruit.service;

import com.rocket.front.project.recruit.adapter.RecruitAdapter;
import com.rocket.front.project.recruit.domain.request.AccessUserRequest;
import com.rocket.front.project.recruit.domain.response.RecruitCardResponse;
import com.rocket.front.project.recruit.domain.response.RecruitTagResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class RecruitServiceImpl implements RecruitService {

    private final RecruitAdapter recruitAdapter;

    @Override
    public Map<String, String> validateHandling(BindingResult bindingResult) {
        Map<String, String> validatorError = new HashMap<>();

        for(FieldError error : bindingResult.getFieldErrors()) {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            validatorError.put(fieldName, errorMessage);
        }

        return validatorError;
    }

    @Override
    public Page<RecruitCardResponse> getRecruitsList(Pageable pageable, Long type, String position, Long field, AccessUserRequest request) {
        return recruitAdapter.getRecruitsList(pageable, type, position, field, request);
    }

    @Override
    public RecruitTagResponse getRecruitTagList() {
        return recruitAdapter.getRecruitTagList();
    }
}
