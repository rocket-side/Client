package com.rocket.front.member.adapter;

import com.rocket.front.member.domain.request.MemberSignUpRequest;
import com.rocket.front.member.domain.request.PositionRegisterRequest;
import com.rocket.front.member.domain.request.PreferenceRegisterRequest;
import com.rocket.front.member.domain.response.*;
import com.rocket.front.properties.MemberProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAdapter {

    private final MemberProperties memberProperties;

    private final RestTemplate restTemplate;

    /**
     * email을 통해서 해당 계정이 중복되었는지 조회합니다.
     *
     * @param email 멤버 이메일
     * @return 중복 여부
     * @HTTP method GET
     */
    public boolean existByEmail(String email) {
        URI uri = getUri("/exist/" + email);

        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(uri, Boolean.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from checking existing Member Email. status:{}, email:{}", responseEntity.getStatusCode(), email);
            throw new RuntimeException("Error from checking existing Member Email. " + responseEntity.getStatusCode() + ", " + email);
        }

        Boolean responseEntityBody = responseEntity.getBody();
        if (responseEntityBody != null) {
            return responseEntityBody;
        } else {
            log.error("ResponseEntity body is null:{}, email:{}", uri, email);
            throw new RuntimeException("ResponseEntity body is null: " + uri + email);
        }
    }

    /**
     * 회원가입 처리를 진행합니다.
     *
     * @param request 저장할 멤버 정보
     * @HTTP method POST
     */
    public void signUp(@Valid MemberSignUpRequest request) {
        HttpEntity<MemberSignUpRequest> requestEntity = new HttpEntity<>(request, getHttpHeader());

        URI uri = getUri("/register");

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange(uri, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Void>() {
                });

        if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            log.error("Error from Sign Up. status:{}", responseEntity.getStatusCode());
            throw new RuntimeException("Error from Sign Up. " + responseEntity.getStatusCode());
        }
    }

    /**
     * seq를 이용하여 멤버를 조회합니다.
     *
     * @param seq 멤버 시퀀스
     * @return 조회된 멤버 정보
     * @HTTP method GET
     */
    public MemberInfoResponse getMemberInfoBySeq(Long seq) {
        URI uri = getUri("/seq/" + seq);

        ResponseEntity<MemberInfoResponse> responseEntity = restTemplate.getForEntity(uri, MemberInfoResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Member by seq. status:{}, memberSeq:{}", responseEntity.getStatusCode(), seq);
            throw new RuntimeException("Error from getting Member by seq. " + responseEntity.getStatusCode() + ", " + seq);
        }

        return responseEntity.getBody();
    }

    /**
     * email을 이용하여 멤버를 조회합니다. (로그인용)
     *
     * @param email 멤버 이메일
     * @return 조회된 멤버 정보
     * @HTTP method GET
     */
    public MemberLoginInfoResponseDto getSignInMemberInfoByEmail(String email) {
        URI uri = getUri("/email/" + email);

        ResponseEntity<MemberLoginInfoResponseDto> responseEntity = restTemplate.getForEntity(uri, MemberLoginInfoResponseDto.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from Sign In. status:{}, email:{}", responseEntity.getStatusCode(), email);
            throw new RuntimeException("Error from Sign In. " + responseEntity.getStatusCode() + ", " + email);
        }

        return responseEntity.getBody();
    }



    /**
     * 멤버 정보를 수정합니다.
     *
     * @param seq     멤버 시퀀스
     * @param request 수정할 멤버 정보
     * @HTTP method PATCH
     */
    public void updateMemberInfo(Long seq, MemberSignUpRequest request) {
        HttpEntity<MemberSignUpRequest> requestEntity = new HttpEntity<>(request, getHttpHeader());

        URI uri = getUri("/update/" + seq);

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange(uri, HttpMethod.PATCH, requestEntity, new ParameterizedTypeReference<Void>() {
                });

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from updating Member Information. status:{}, memberSeq:{}", responseEntity.getStatusCode(), seq);
            throw new RuntimeException("Error from updating Member Information. " + responseEntity.getStatusCode() + ", " + seq);
        }
    }

    /**
     * seq로 해당 멤버를 삭제합니다.
     *
     * @param seq 멤버 시퀀스
     * @HTTP method DELETE
     */
    public void deleteMember(Long seq) {
        URI uri = getUri("/delete/" + seq);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, null, new ParameterizedTypeReference<Void>() {
        });

        if (responseEntity.getStatusCode() != HttpStatus.NO_CONTENT) {
            log.error("Error from deleting Member. status:{}, memberSeq:{}", responseEntity.getStatusCode(), seq);
            throw new RuntimeException("Error from deleting Member. " + responseEntity.getStatusCode() + ", " + seq);
        }
    }

    /**
     * 멤버 seq로 해당 멤버의 레벨을 조회합니다.
     *
     * @param seq 멤버 시퀀스
     * @return 조회된 레벨 객체
     * @HTTP method GET
     */
    public LevelResponse getLevel(Long seq) {
        URI uri = getUri("/level/" + seq);

        ResponseEntity<LevelResponse> responseEntity = restTemplate.getForEntity(uri, LevelResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Member Level. status:{}, memberSeq:{}", responseEntity.getStatusCode(), seq);
            throw new RuntimeException("Error from getting Member Level. " + responseEntity.getStatusCode() + ", " + seq);
        }

        return responseEntity.getBody();
    }

    /**
     * 멤버 seq와 레벨 seq로 해당 멤버의 레벨을 수정합니다.
     *
     * @param memberSeq 멤버 시퀀스
     * @param levelSeq  레벨 시퀀스
     * @HTTP method PATCH
     */
    public void updateLevel(Long memberSeq, Long levelSeq) {
        URI uri = getUri("/level/update/" + memberSeq + "/" + levelSeq);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.PATCH, null, new ParameterizedTypeReference<Void>() {
        });

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from updating Member Level. status:{}, memberSeq:{}, levelSeq:{}", responseEntity.getStatusCode(), memberSeq, levelSeq);
            throw new RuntimeException("Error from updating Member Level. " + responseEntity.getStatusCode() + ", " + memberSeq + ", " + levelSeq);
        }
    }

    /**
     * 멤버 seq로 해당 멤버의 관심 분야를 조회합니다.
     *
     * @param seq 멤버 시퀀스
     * @return 조회된 관심 분야
     * @HTTP method GET
     */
    public PreferenceResponse getPreference(Long seq) {
        URI uri = getUri("/preference/" + seq);

        ResponseEntity<PreferenceResponse> responseEntity = restTemplate.getForEntity(uri, PreferenceResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Member Preference. status:{}, memberSeq:{}", responseEntity.getStatusCode(), seq);
            throw new RuntimeException("Error from getting Member Preference. " + responseEntity.getStatusCode() + ", " + seq);
        }

        return responseEntity.getBody();
    }

    /**
     * 멤버 seq와 관심 분야 seq list로 관심 분야를 추가합니다.
     *
     * @param memberSeq 멤버 시퀀스
     * @param request 추가할 관심 분야 seq list
     * @HTTP method POST
     */
    public void registerPreference(Long memberSeq, @Valid PreferenceRegisterRequest request) {
        HttpEntity<PreferenceRegisterRequest> requestEntity = new HttpEntity<>(request, getHttpHeader());

        URI uri = getUri("/preference/register/" + memberSeq);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Void>() {
        });

        if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            log.error("Error from register Member Preference. status:{}, memberSeq:{}", responseEntity.getStatusCode(), memberSeq);
            throw new RuntimeException("Error from register Member Preference. " + responseEntity.getStatusCode() + ", " + memberSeq);
        }
    }

    /**
     * 멤버 seq와 관심 분야 seq로 관심 분야를 삭제합니다.
     *
     * @param memberSeq     멤버 시퀀스
     * @param preferenceSeq 삭제할 관심 분야 시퀀스
     * @HTTP method DELETE
     */
    public void deletePreference(Long memberSeq, Long preferenceSeq) {
        URI uri = getUri("/preference/delete/" + memberSeq + "/" + preferenceSeq);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, null, new ParameterizedTypeReference<Void>() {
        });

        if (responseEntity.getStatusCode() != HttpStatus.NO_CONTENT) {
            log.error("Error from deleting Member Preference. status:{}, memberSeq:{}, preferenceSeq:{}", responseEntity.getStatusCode(), memberSeq, preferenceSeq);
            throw new RuntimeException("Error from deleting Member Preference. " + responseEntity.getStatusCode() + ", " + memberSeq + ", " + preferenceSeq);
        }
    }

    /**
     * 멤버 seq로 해당 멤버의 권한을 조회합니다.
     *
     * @param seq 멤버 시퀀스
     * @return 조회된 권한
     * @HTTP method GET
     */
    public RoleResponse getRole(Long seq) {
        URI uri = getUri("/role/" + seq);

        ResponseEntity<RoleResponse> responseEntity = restTemplate.getForEntity(uri, RoleResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Member Role. status:{}, memberSeq:{}", responseEntity.getStatusCode(), seq);
            throw new RuntimeException("Error from getting Member Role. " + responseEntity.getStatusCode() + ", " + seq);
        }

        return responseEntity.getBody();
    }

    /**
     * 멤버 seq와 권한 seq로 해당 멤버의 권한을 수정합니다.
     *
     * @param memberSeq 멤버 시퀀스
     * @param roleSeq   권한 시퀀스
     * @HTTP method PATCH
     */
    public void updateRole(Long memberSeq, Long roleSeq) {
        URI uri = getUri("/role/update/" + memberSeq + "/" + roleSeq);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.PATCH, null, new ParameterizedTypeReference<Void>() {
        });

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from updating Member Role. status:{}, memberSeq:{}, roleSeq:{}", responseEntity.getStatusCode(), memberSeq, roleSeq);
            throw new RuntimeException("Error from updating Member Role. " + responseEntity.getStatusCode() + ", " + memberSeq + ", " + roleSeq);
        }
    }

    /**
     * 멤버 seq로 해당 멤버의 포지션을 조회합니다.
     *
     * @param seq 멤버 시퀀스
     * @return 조회된 포지션
     * @HTTP method GET
     */
    public PositionResponse getPosition(Long seq) {
        URI uri = getUri("/position/" + seq);

        ResponseEntity<PositionResponse> responseEntity = restTemplate.getForEntity(uri, PositionResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Member Position. status:{}, memberSeq:{}", responseEntity.getStatusCode(), seq);
            throw new RuntimeException("Error from getting Member Position. " + responseEntity.getStatusCode() + ", " + seq);
        }

        return responseEntity.getBody();
    }

    /**
     * 멤버 seq와 포지션 seq list로 포지션을 추가합니다.
     *
     * @param memberSeq     멤버 시퀀스
     * @param request 추가할 포지션 seq list
     * @HTTP method POST
     */
    public void registerPosition(Long memberSeq, @Valid PositionRegisterRequest request) {
        HttpEntity<PositionRegisterRequest> requestEntity = new HttpEntity<>(request, getHttpHeader());

        URI uri = getUri("/position/register/" + memberSeq);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Void>() {
        });

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from register Member Position. status:{}, memberSeq:{}", responseEntity.getStatusCode(), memberSeq);
            throw new RuntimeException("Error from register Member Position. " + responseEntity.getStatusCode() + ", " + memberSeq);
        }
    }

    /**
     * 멤버 seq와 포지션 seq로 해당 멤버의 포지션을 삭제합니다.
     *
     * @param memberSeq   멤버 시퀀스
     * @param positionSeq 포지션 시퀀스
     * @HTTP method DELETE
     */
    public void deletePosition(Long memberSeq, Long positionSeq) {
        URI uri = getUri("/position/delete/" + memberSeq + "/" + positionSeq);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, null, new ParameterizedTypeReference<Void>() {
        });

        if (responseEntity.getStatusCode() != HttpStatus.NO_CONTENT) {
            log.error("Error from deleting Member Position. status:{}, memberSeq:{}, positionSeq:{}", responseEntity.getStatusCode(), memberSeq, positionSeq);
            throw new RuntimeException("Error from deleting Member Position. " + responseEntity.getStatusCode() + ", " + memberSeq + ", " + positionSeq);
        }
    }

    public static HttpHeaders getHttpHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

    public URI getUri(String path) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromPath("/member/api/" + path)
                .scheme("http")
                .host(memberProperties.getHost())
                .port(memberProperties.getPort());

        return uriComponentsBuilder.build().encode().toUri();
    }
}

