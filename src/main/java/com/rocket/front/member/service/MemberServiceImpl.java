package com.rocket.front.member.service;

import com.rocket.front.member.adapter.MemberAdapter;
import com.rocket.front.member.domain.request.MemberSignUpRequest;
import com.rocket.front.member.domain.response.MemberInfoResponse;
import com.rocket.front.member.domain.response.PositionResponse;
import com.rocket.front.member.domain.response.PreferenceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberAdapter memberAdapter;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(MemberSignUpRequest request) {
        memberAdapter.signUp(request);
    }

    @Override
    public MemberInfoResponse getMemberInfo(Long memberSeq) {
        return memberAdapter.getMemberInfoBySeq(memberSeq);
    }

    @Override
    public PreferenceResponse getMemberPreference(Long memberSeq) {
        return memberAdapter.getPreference(memberSeq);
    }

    @Override
    public PositionResponse getMemberPosition(Long memberSeq) {
        return memberAdapter.getPosition(memberSeq);
    }

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
    public String passwordEncoding(String password) {
        return passwordEncoder.encode(password);
    }

}
