package com.rocket.front.project.introduction.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocket.front.project.introduction.domain.request.AccessUserRequest;
import com.rocket.front.project.introduction.domain.response.CommentResponse;
import com.rocket.front.project.introduction.domain.response.IntroductionForCardResponse;
import com.rocket.front.project.introduction.domain.response.IntroductionResponse;
import com.rocket.front.project.introduction.domain.response.PageDto;
import com.rocket.front.project.recruit.domain.response.RecruitCardResponse;
import com.rocket.front.properties.ProjectProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
public class IntroductionAdapter {

    private final ProjectProperties projectProperties;

    private final RestTemplate restTemplate;

    private final String INTRODUCTION_URI = "/project/api/introduces";

    /**
     * 프로젝트 목록 조회 - 미리 보기 카드에 들어갈 내용만 넣어둔 리스트
     * @param pageable 페이지 및 정렬 정보
     * @param type 공고 유형
     * @param field 공고 분야
     * @param memberSeq 사용자 정보
     * @return 카드 형식의 공고 목록 응답
     * @throws RuntimeException 요청 실패 시 발생
     */
    public PageDto<IntroductionForCardResponse> getIntroductionList(Pageable pageable, Long type, Long field, String memberSeq) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", pageable.getPageNumber());
//        params.put("type", type);
//        params.put("field", field);
        URI uri = getUri(params, INTRODUCTION_URI);

        HttpEntity<AccessUserRequest> requestEntity = new HttpEntity<>(convertToAccessUserRequest(memberSeq),getHttpHeader());

        try {
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(requestEntity));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try{
            ResponseEntity<PageDto<IntroductionForCardResponse>> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<PageDto<IntroductionForCardResponse>>() {}
            );

            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Error from getting Recruit List. " + responseEntity.getStatusCode() + ", " + pageable.getPageNumber() + ", " + type + ", " + field);
            }

            return responseEntity.getBody();
        }catch (Exception e) {
            log.info(":::::Error  {}:::::",e.toString());
        }

//        ResponseEntity<Page<IntroductionForCardResponse>> responseEntity = restTemplate.exchange(
//                uri,
//                HttpMethod.POST,
//                requestEntity,
//                new ParameterizedTypeReference<>() {}
//        );
//
//        if (responseEntity.getStatusCode() != HttpStatus.OK) {
//            throw new RuntimeException("Error from getting Recruit List. " + responseEntity.getStatusCode() + ", " + pageable.getPageNumber() + ", " + type + ", " + field);
//        }

        return null;
    }

    /**
     * 해당 소개글 조회
     * @param recruitSeq
     * @return IntroductionResponse 200
     */
    public IntroductionResponse getIntroduction(String recruitSeq) {
        Map<String, Object> params = new HashMap<>();
        URI uri = getUri(params, INTRODUCTION_URI + "/" + recruitSeq);

        ResponseEntity<IntroductionResponse> responseEntity = restTemplate.getForEntity(uri, IntroductionResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error from getting Introduction " + responseEntity.getStatusCode());
        }

        return responseEntity.getBody();
    }

    /**
     *  해당 소개글의 모든 댓글 조회
     * @param recruitSeq
     * @return
     */
    public List<CommentResponse> getIntroductionComments(String recruitSeq) {
        Map<String, Object> params = new HashMap<>();
        URI uri = getUri(params, INTRODUCTION_URI + "/" + recruitSeq + "/comments");

        ResponseEntity<List<CommentResponse>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error from getting Comments " + responseEntity.getStatusCode());
        }

        return responseEntity.getBody();
    }

    //TODO boolean 안된다
    /**
     *  소개글의 접근하는 유저가 소개글 작성자(공고리더)인지 확인
     * @param recruitSeq
     * @param
     * @return
     */
    public boolean isIntroductionWriter(String recruitSeq,String memberSeq){
        Map<String, Object> params = new HashMap<>();
        URI uri = getUri(params, INTRODUCTION_URI + "/iswriter/" + recruitSeq);

        HttpEntity<AccessUserRequest> requestEntity = new HttpEntity<>(convertToAccessUserRequest(memberSeq),getHttpHeader());

        try {
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(requestEntity));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error from getting introductionWriter " + responseEntity.getStatusCode());
        }

        return Boolean.TRUE.equals(responseEntity.getBody());
    }



    /**
     * 댓글에 접근하는 유저가 댓글작성자 인지 확인
     * @param commentSeq
     * @param memberSeq
     * @return
     */
    public Boolean isRedirectUser(String commentSeq, String memberSeq){
        Map<String, Object> params = new HashMap<>();
        URI uri = getUri(params, INTRODUCTION_URI + "/isredirect/" + commentSeq);
        HttpHeaders httpHeaders = getHttpHeader();

        Long parsedMemberSeq = null;
        if (memberSeq != null) {
            parsedMemberSeq = Long.parseLong(memberSeq);
        }

        HttpEntity<AccessUserRequest> requestEntity = new HttpEntity<>(new AccessUserRequest(parsedMemberSeq),httpHeaders);

        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error from getting redirectUser " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
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

    private AccessUserRequest convertToAccessUserRequest(String memberSeq) {
        Long parsedMemberSeq = null;
        if (memberSeq != null) {
            parsedMemberSeq = Long.parseLong(memberSeq);
        }
        return new AccessUserRequest(parsedMemberSeq);
    }
}
