package com.rocket.front.project.introduction.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocket.front.project.introduction.domain.request.AccessUser;
import com.rocket.front.project.introduction.domain.response.CommentResponse;
import com.rocket.front.project.introduction.domain.response.IntroductionResponse;
import com.rocket.front.properties.ProjectProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

    private final String RECRUIT_URI = "/project/api/introduces";

    /**
     * 해당 소개글 조회
     * @param recruitSeq
     * @return IntroductionResponse 200
     */
    public IntroductionResponse getIntroduction(String recruitSeq) {
        Map<String, Object> params = new HashMap<>();
        URI uri = getUri(params, RECRUIT_URI + "/" + recruitSeq);

        ResponseEntity<IntroductionResponse> responseEntity = restTemplate.getForEntity(uri, IntroductionResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
//            log.error("Error from getting Member Preference. status: {}, param: {}", responseEntity.getStatusCode(), seq);
//            throw new RuntimeException("Error from getting Member Preference. " + responseEntity.getStatusCode() + ", " + seq);
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
        URI uri = getUri(params, RECRUIT_URI + "/" + recruitSeq + "/comments");

        ResponseEntity<List<CommentResponse>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CommentResponse>>() {}
        );

        return responseEntity.getBody();
    }

    /**
     *  소개글의 접근하는 유저가 소개글 작성자(공고리더)인지 확인
     * @param recruitSeq
     * @param
     * @return
     */
    public Boolean isIntroductionWriter(String recruitSeq,String memberSeq){
        Map<String, Object> params = new HashMap<>();
        URI uri = getUri(params, RECRUIT_URI + "/iswriter/" + recruitSeq);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Long parsedMemberSeq = null;
        if (memberSeq != null) {
            parsedMemberSeq = Long.parseLong(memberSeq);
        }
        HttpEntity<AccessUser> requestEntity = new HttpEntity<>(new AccessUser(parsedMemberSeq),httpHeaders);

        try {
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(requestEntity));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Boolean>() {}
        );

        return responseEntity.getBody();
    }



    /**
     * 댓글에 접근하는 유저가 댓글작성자 인지 확인
     * @param commentSeq
     * @param memberSeq
     * @return
     */
    public Boolean isRedirectUser(String commentSeq, String memberSeq){
        Map<String, Object> params = new HashMap<>();
        URI uri = getUri(params, RECRUIT_URI + "/isredirect/" + commentSeq);
        HttpHeaders httpHeaders = getHttpHeader();

        Long parsedMemberSeq = null;
        if (memberSeq != null) {
            parsedMemberSeq = Long.parseLong(memberSeq);
        }

        HttpEntity<AccessUser> requestEntity = new HttpEntity<>(new AccessUser(parsedMemberSeq),httpHeaders);

        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Boolean>() {}
        );
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
}
