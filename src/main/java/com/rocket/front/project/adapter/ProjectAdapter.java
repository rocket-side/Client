package com.rocket.front.project.adapter;

import com.rocket.front.member.domain.response.PreferenceResponse;
import com.rocket.front.properties.ProjectProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class ProjectAdapter {

    private final ProjectProperties projectProperties;

    private final RestTemplate restTemplate;

    /**
     * 예제 - 멤버 seq로 해당 멤버의 관심 분야를 조회합니다.
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
