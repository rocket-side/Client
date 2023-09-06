package com.rocket.front.project.recruit.adapter;

import com.rocket.front.project.recruit.domain.request.AccessUserRequest;
import com.rocket.front.project.recruit.domain.response.RecruitCardResponse;
import com.rocket.front.project.recruit.domain.response.RecruitResponse;
import com.rocket.front.project.recruit.domain.response.RecruitTagResponse;
import com.rocket.front.properties.ProjectProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecruitAdapter {

    private final ProjectProperties projectProperties;

    private final RestTemplate restTemplate;

    private final String RECRUIT_URI = "/project/api/recruits";

    /**
     * 공고 목록 조회 - 미리 보기 카드에 들어갈 내용만 넣어둔 리스트
     * @param pageable 페이지 및 정렬 정보
     * @param type 공고 유형
     * @param position 공고 포지션
     * @param field 공고 분야
     * @param request 사용자 액세스 요청
     * @return 카드 형식의 공고 목록 응답
     * @throws RuntimeException 요청 실패 시 발생
     */
    public Page<RecruitCardResponse> getRecruitsList(Pageable pageable, Long type, String position, Long field, @Valid AccessUserRequest request) {
        HttpEntity<AccessUserRequest> requestEntity = new HttpEntity<>(request, getHttpHeader());

        Map<String, Object> params = new HashMap<>();
        params.put("page", pageable.getPageNumber());
        params.put("type", type);
        params.put("position", position);
        params.put("field", field);
        URI uri = getUri(params, RECRUIT_URI);

        log.info(":::::recruit-list check   {}:::::",uri.toString());

        ResponseEntity<Page<RecruitCardResponse>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Page<RecruitCardResponse>>() {}
        );

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Recruit List. status: {}, params:: page: {}, type: {}, position: {}, field: {}", responseEntity.getStatusCode(), pageable.getPageNumber(), type, position, field);
            throw new RuntimeException("Error from getting Recruit List. " + responseEntity.getStatusCode() + ", " + pageable.getPageNumber() + ", " + type + ", " + position + ", " + field);
        }

        return responseEntity.getBody();
    }

    /**
     * 모든 태그 종류를 조회
     * @return 전체 태그 목록 응답
     * @throws RuntimeException 요청 실패 시 발생
     */
    public RecruitTagResponse getRecruitTagList() {
        Map<String, Object> params = new HashMap<>();
        URI uri = getUri(params, RECRUIT_URI + "/types");

        ResponseEntity<RecruitTagResponse> responseEntity = restTemplate.getForEntity(uri, RecruitTagResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Tag List. status: {}", responseEntity.getStatusCode());
            throw new RuntimeException("Error from getting Tag List. " + responseEntity.getStatusCode());
        }

        return responseEntity.getBody();
    }

    /**
     * 해당 공고 조회
     * @param recruitSeq 공고글 시퀀스
     * @return 공고글 정보 응답
     * @throws RuntimeException 요청 실패 시 발생
     */
    public RecruitResponse getRecruit(Long recruitSeq) {
        Map<String, Object> params = new HashMap<>();
        URI uri = getUri(params, RECRUIT_URI + "/" + recruitSeq);

        ResponseEntity<RecruitResponse> responseEntity = restTemplate.getForEntity(uri, RecruitResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Recruit. status: {}, recruitSeq: {}", responseEntity.getStatusCode(), recruitSeq);
            throw new RuntimeException("Error from getting Recruit. " + responseEntity.getStatusCode() + ", " + recruitSeq);
        }

        return responseEntity.getBody();
    }

    /**
     * 공고 수정 or 삭제시 접근 멤버가 공고 생성자가 맞는지 확인
     * @param recruitSeq 공고글 시퀀스
     * @param request 사용자 액세스 요청
     * @return 공고 생성자 여부 응답
     * @throws RuntimeException 요청 실패 시 발생
     */
    public boolean isGroupLeader(Long recruitSeq, @Valid AccessUserRequest request) {
        HttpEntity<AccessUserRequest> requestEntity = new HttpEntity<>(request, getHttpHeader());

        Map<String, Object> params = new HashMap<>();
        URI uri = getUri(params, RECRUIT_URI + "/" + recruitSeq);

        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Boolean.class);

        if(responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting isGroupleader. status:{}, recruitSeq:{}", responseEntity.getStatusCode(), recruitSeq);
            throw new RuntimeException("Error from getting isGroupleader. " + responseEntity.getStatusCode() + ", " + recruitSeq);
        }

        Boolean responseEntityBody = responseEntity.getBody();
        if (responseEntityBody != null) {
            return responseEntityBody;
        } else {
            log.error("ResponseEntity body is null:{}, recruitSeq:{}", uri, recruitSeq);
            throw new RuntimeException("ResponseEntity body is null: " + uri + recruitSeq);
        }
    }

    public static HttpHeaders getHttpHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

    public URI getUri(Map<String, Object> params, String path) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromPath(path)
                .scheme("http")
                .host(projectProperties.getHost())
                .port(projectProperties.getPort());

        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                uriComponentsBuilder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        return uriComponentsBuilder.build().encode().toUri();
    }
}