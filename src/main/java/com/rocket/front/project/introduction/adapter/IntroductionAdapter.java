package com.rocket.front.project.introduction.adapter;

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

//    public Page<RecruitCardResponse> getIntroductionList(Pageable pageable) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("page",pageable.getPageNumber());
//        URI uri = getUri(params, RECRUIT_URI);
//
//        log.info(":::::recruit-list check   {}:::::",uri.toString());
//
//        ResponseEntity<Page<RecruitCardResponse>> responseEntity = restTemplate.exchange(
//                uri,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<Page<RecruitCardResponse>>() {}
//        );
//
//        if (responseEntity.getStatusCode() != HttpStatus.OK) {
////            log.error("Error from getting Member Preference. status: {}, param: {}", responseEntity.getStatusCode(), seq);
////            throw new RuntimeException("Error from getting Member Preference. " + responseEntity.getStatusCode() + ", " + seq);
//            //TODO 시연이한테 물어보자
//        }
//
//        return responseEntity.getBody();
//    }

//    public RecruitTagResponse getRecruitTagList() {
//        Map<String, Object> params = new HashMap<>();
//        URI uri = getUri(params, RECRUIT_URI + "/types");
//
//        ResponseEntity<RecruitTagResponse> responseEntity = restTemplate.getForEntity(uri, RecruitTagResponse.class);
//
//        if (responseEntity.getStatusCode() != HttpStatus.OK) {
////            log.error("Error from getting Member Preference. status: {}, param: {}", responseEntity.getStatusCode(), seq);
////            throw new RuntimeException("Error from getting Member Preference. " + responseEntity.getStatusCode() + ", " + seq);
//        }
//
//        return responseEntity.getBody();
//    }

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

    // TODO session에 저장된 memberSeq 가져와서 넣어줘야 함 객체로???
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
