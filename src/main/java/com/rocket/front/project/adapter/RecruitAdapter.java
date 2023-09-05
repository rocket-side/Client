package com.rocket.front.project.adapter;

import com.rocket.front.project.domain.recruit.response.RecruitCardResponse;
import com.rocket.front.project.domain.recruit.response.RecruitResponse;
import com.rocket.front.project.domain.recruit.response.RecruitTagResponse;
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

    public Page<RecruitCardResponse> getRecruitsList(Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        params.put("page",pageable.getPageNumber());
        URI uri = getUri(params, RECRUIT_URI);

        log.info(":::::recruit-list check   {}:::::",uri.toString());

        ResponseEntity<Page<RecruitCardResponse>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Page<RecruitCardResponse>>() {}
        );

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
//            log.error("Error from getting Member Preference. status: {}, param: {}", responseEntity.getStatusCode(), seq);
//            throw new RuntimeException("Error from getting Member Preference. " + responseEntity.getStatusCode() + ", " + seq);
            //TODO 시연이한테 물어보자
        }

        return responseEntity.getBody();
    }

    public RecruitTagResponse getRecruitTagList() {
        Map<String, Object> params = new HashMap<>();
        URI uri = getUri(params, RECRUIT_URI + "/types");

        ResponseEntity<RecruitTagResponse> responseEntity = restTemplate.getForEntity(uri, RecruitTagResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
//            log.error("Error from getting Member Preference. status: {}, param: {}", responseEntity.getStatusCode(), seq);
//            throw new RuntimeException("Error from getting Member Preference. " + responseEntity.getStatusCode() + ", " + seq);
        }

        return responseEntity.getBody();
    }

    public RecruitResponse getRecruit(String recruitSeq) {
        Map<String, Object> params = new HashMap<>();
        URI uri = getUri(params, RECRUIT_URI + "/" + recruitSeq);

        ResponseEntity<RecruitResponse> responseEntity = restTemplate.getForEntity(uri, RecruitResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
//            log.error("Error from getting Member Preference. status: {}, param: {}", responseEntity.getStatusCode(), seq);
//            throw new RuntimeException("Error from getting Member Preference. " + responseEntity.getStatusCode() + ", " + seq);
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
}
