package com.rocket.front.member.adapter;

import com.rocket.front.member.domain.request.MemberSignUpRequest;
import com.rocket.front.member.domain.request.PreferenceRegisterRequest;
import com.rocket.front.member.domain.response.*;
import com.rocket.front.properties.FrontProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/member/api/")
public class MemberAdapter {

    private final RestTemplate restTemplate;
    private final FrontProperties frontProperties;

    public MemberAdapter(RestTemplate restTemplate, FrontProperties frontProperties) {
        this.restTemplate = restTemplate;
        this.frontProperties = frontProperties;
    }

    /**
     * email을 통해서 해당 계정이 중복되었는지 조회합니다.
     *
     * @param email 멤버 이메일
     * @return 중복 여부
     * @HTTP method GET
     */
    public boolean existByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        URI uri = getUri(params, "/exist/{email}");

        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(uri, Boolean.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from checking existing Member Email. status:{}, param:{}", responseEntity.getStatusCode(), email);
            throw new RuntimeException("Error from checking existing Member Email. " + responseEntity.getStatusCode() + ", " + email);
        }

        Boolean responseEntityBody = responseEntity.getBody();
        if (responseEntityBody != null) {
            return responseEntityBody;
        } else {
            log.error("ResponseEntity body is null: {}, email: {}", uri, email);
            throw new RuntimeException("ResponseEntity body is null: " + uri + email);
        }
    }

    /**
     * 회원가입 처리를 진행합니다.
     *
     * @param request 저장할 멤버 정보
     * @HTTP method POST
     */
    public void signUp(MemberSignUpRequest request) {
        HttpEntity<MemberSignUpRequest> requestEntity = new HttpEntity<>(request, getHttpHeader());

        URI uri = getUri(null, "/register");

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange(uri, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Void>() {
                });

        if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            log.error("Error from Sign Up. status: {}", responseEntity.getStatusCode());
            throw new RuntimeException("Error from Sign Up. " + responseEntity.getStatusCode());
        }
    }

    /**
     * seq를 이용하여 멤버를 조회합니다. (로그인용)
     *
     * @param seq 멤버 시퀀스
     * @return 조회된 멤버 정보
     * @HTTP method GET
     */

    public MemberInfoResponse getMemberInfoBySeq(Long seq) {
        Map<String, Object> params = new HashMap<>();
        params.put("seq", seq);
        URI uri = getUri(params, "/seq/{seq}");

        ResponseEntity<MemberInfoResponse> responseEntity = restTemplate.getForEntity(uri, MemberInfoResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Member by seq. status:{}, param:{}", responseEntity.getStatusCode(), seq);
            throw new RuntimeException("Error from getting Member by seq. " + responseEntity.getStatusCode() + ", " + seq);
        }

        return responseEntity.getBody();
    }

    /**
     * email을 이용하여 멤버를 조회합니다.
     *
     * @param email 멤버 이메일
     * @return 조회된 멤버 정보
     * @HTTP method GET
     */
    public MemberInfoResponse getMemberInfoByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        URI uri = getUri(params, "/email/{email}");

        ResponseEntity<MemberInfoResponse> responseEntity = restTemplate.getForEntity(uri, MemberInfoResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Member by Email. status:{}, param:{}", responseEntity.getStatusCode(), email);
            throw new RuntimeException("Error from getting Member by Email. " + responseEntity.getStatusCode() + ", " + email);
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

        Map<String, Object> params = new HashMap<>();
        params.put("seq", seq);
        URI uri = getUri(params, "/update/{seq}");

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange(uri, HttpMethod.PATCH, requestEntity, new ParameterizedTypeReference<Void>() {
                });

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from updating Member Information. status: {}, param: {}", responseEntity.getStatusCode(), seq);
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
        Map<String, Object> params = new HashMap<>();
        params.put("seq", seq);
        URI uri = getUri(params, "/delete/{seq}");

        ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, null, new ParameterizedTypeReference<Void>() {
        });

        if (responseEntity.getStatusCode() != HttpStatus.NO_CONTENT) {
            log.error("Error from deleting Member. status: {}, param: {}", responseEntity.getStatusCode(), seq);
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
        Map<String, Object> params = new HashMap<>();
        params.put("seq", seq);
        URI uri = getUri(params, "/level/{seq}");

        ResponseEntity<LevelResponse> responseEntity = restTemplate.getForEntity(uri, LevelResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Member Level. status:{}, param: {}", responseEntity.getStatusCode(), seq);
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
        Map<String, Object> params = new HashMap<>();
        params.put("member_seq", memberSeq);
        params.put("level_seq", levelSeq);
        URI uri = getUri(params, "/level/update/{member_seq}/{level_seq}");

        ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.PATCH, null, new ParameterizedTypeReference<Void>() {
        });

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from updating Member Level. status: {}, param: {}, {}", responseEntity.getStatusCode(), memberSeq, levelSeq);
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
        Map<String, Object> params = new HashMap<>();
        params.put("seq", seq);
        URI uri = getUri(params, "/preference/{seq}");

        ResponseEntity<PreferenceResponse> responseEntity = restTemplate.getForEntity(uri, PreferenceResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Member Preference. status: {}, param: {}", responseEntity.getStatusCode(), seq);
            throw new RuntimeException("Error from getting Member Preference. " + responseEntity.getStatusCode() + ", " + seq);
        }

        return responseEntity.getBody();
    }

    /**
     * 멤버 seq와 관심 분야 seq list로 관심 분야를 추가합니다.
     *
     * @param seq     멤버 시퀀스
     * @param request 추가할 관심 분야 seq list
     * @HTTP method POST
     */
    public void registerPreference(Long seq, PreferenceRegisterRequest request) {
        HttpEntity<PreferenceRegisterRequest> requestEntity = new HttpEntity<>(request, getHttpHeader());

        Map<String, Object> params = new HashMap<>();
        params.put("seq", seq);
        URI uri = getUri(params, "/preference/register/{seq}");

        ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Void>() {
        });

        if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            log.error("Error from register Member Preference. status: {}, param: {}", responseEntity.getStatusCode(), seq);
            throw new RuntimeException("Error from register Member Preference. " + responseEntity.getStatusCode() + ", " + seq);
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
        Map<String, Object> params = new HashMap<>();
        params.put("member_seq", memberSeq);
        params.put("preference_seq", preferenceSeq);
        URI uri = getUri(params, "/preference/delete/{member_seq}/{preference_seq}");

        ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, null, new ParameterizedTypeReference<Void>() {
        });

        if (responseEntity.getStatusCode() != HttpStatus.NO_CONTENT) {
            log.error("Error from deleting Member Preference. status: {}, param: {}, {}", responseEntity.getStatusCode(), memberSeq, preferenceSeq);
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
        Map<String, Object> params = new HashMap<>();
        params.put("seq", seq);
        URI uri = getUri(params, "/role/{seq}");

        ResponseEntity<RoleResponse> responseEntity = restTemplate.getForEntity(uri, RoleResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Member Role. status: {}, param: {}", responseEntity.getStatusCode(), seq);
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
        Map<String, Object> params = new HashMap<>();
        params.put("member_seq", memberSeq);
        params.put("role_seq", roleSeq);
        URI uri = getUri(params, "/role/update/{member_seq}/{role_seq}");

        ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.PATCH, null, new ParameterizedTypeReference<Void>() {
        });

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from updating Member Role. status: {}, param: {}, {}", responseEntity.getStatusCode(), memberSeq, roleSeq);
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
        Map<String, Object> params = new HashMap<>();
        params.put("seq", seq);
        URI uri = getUri(params, "/position/{seq}");

        ResponseEntity<PositionResponse> responseEntity = restTemplate.getForEntity(uri, PositionResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Member Position. status: {}, param: {}", responseEntity.getStatusCode(), seq);
            throw new RuntimeException("Error from getting Member Position. " + responseEntity.getStatusCode() + ", " + seq);
        }

        return responseEntity.getBody();
    }

    /**
     * 멤버 seq와 포지션 seq list로 포지션을 추가합니다.
     *
     * @param seq     멤버 시퀀스
     * @param request 추가할 포지션 seq list
     * @HTTP method POST
     */
    public void registerPosition(Long seq, PositionResponse request) {
        HttpEntity<PositionResponse> requestEntity = new HttpEntity<>(request, getHttpHeader());

        Map<String, Object> params = new HashMap<>();
        params.put("seq", seq);
        URI uri = getUri(params, "/position/register/{seq}");

        ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Void>() {
        });

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from register Member Position. status: {}, param: {}", responseEntity.getStatusCode(), seq);
            throw new RuntimeException("Error from register Member Position. " + responseEntity.getStatusCode() + ", " + seq);
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
        Map<String, Object> params = new HashMap<>();
        params.put("member_seq", memberSeq);
        params.put("position_seq", positionSeq);
        URI uri = getUri(params, "/position/delete/{member_seq}/{position_seq}");

        ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, null, new ParameterizedTypeReference<Void>() {
        });

        if (responseEntity.getStatusCode() != HttpStatus.NO_CONTENT) {
            log.error("Error from deleting Member Position. status: {}, param: {}, {}", responseEntity.getStatusCode(), memberSeq, positionSeq);
            throw new RuntimeException("Error from deleting Member Position. " + responseEntity.getStatusCode() + ", " + memberSeq + ", " + positionSeq);
        }
    }

    public static HttpHeaders getHttpHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

//    public URI getUri(String param, String path) {
//        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
//                .fromPath(path)
//                .scheme("http")
//                .host(frontProperties.getHost())
//                .port(frontProperties.getPort());
//
//        if (Objects.nonNull(param)) {
//            return uriComponentsBuilder.queryParam("param", param).build().encode().toUri();
//        } else {
//            return uriComponentsBuilder.build(false).encode().toUri();
//        }
//    }
//
//    public URI getUri(Long param, String path) {
//        return getUri(String.valueOf(param), path);
//    }

    public URI getUri(Map<String, Object> params, String path) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromPath(path)
                .scheme("http")
                .host(frontProperties.getHost())
                .port(frontProperties.getPort());

        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                uriComponentsBuilder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        return uriComponentsBuilder.build().encode().toUri();
    }
}