package com.rocket.front.member.service;

import com.rocket.front.member.domain.request.MemberSignUpRequest;
import com.rocket.front.member.domain.response.MemberInfoResponse;
import com.rocket.front.member.domain.response.PositionResponse;
import com.rocket.front.member.domain.response.PreferenceResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.Map;

@Service
public interface MemberService {

    void signUp(MemberSignUpRequest request);

    MemberInfoResponse getMemberInfo(Long memberSeq);

    PreferenceResponse getMemberPreference(Long memberSeq);

    PositionResponse getMemberPosition(Long memberSeq);

    Map<String, String> validateHandling(BindingResult bindingResult);

    String passwordEncoding(String password);
}
