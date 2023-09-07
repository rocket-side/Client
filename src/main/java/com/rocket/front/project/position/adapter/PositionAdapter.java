package com.rocket.front.project.position.adapter;

import com.rocket.front.project.position.domain.response.ApplicantsResponse;
import com.rocket.front.project.position.domain.response.ApplyStatusResponse;
import com.rocket.front.project.position.domain.response.RecruitCrewResponse;
import com.rocket.front.properties.ProjectProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class PositionAdapter {

    private final ProjectProperties projectProperties;

    private final RestTemplate restTemplate;

    private final String POSITION_URI = "/project/api/recruits";

    /**
     * 공고글 모집 현황 정보 조회
     * @param recruitSeq 공고글 시퀀스
     * @return 모집 정보 응답
     * @throws RuntimeException 요청 실패 시 발생
     */
    public List<ApplyStatusResponse> getApplicantStatusList(Long recruitSeq) {
        URI uri = getUri(POSITION_URI + "/" + recruitSeq);

        ResponseEntity<List<ApplyStatusResponse>> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<ApplyStatusResponse>>() {});

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Recruit ApplicantStatus. status:{}, recruitSeq:{}", responseEntity.getStatusCode(), recruitSeq);
            throw new RuntimeException("Error from getting Recruit ApplicantStatus. " + responseEntity.getStatusCode() + ", " + recruitSeq);
        }

        return responseEntity.getBody();
    }

    /**
     * 해당 공고에 지원한 (대기 중인) 지원자 목록 조회
     * @param recruitSeq 공고글 시퀀스
     * @return 지원자 정보 목록 응답
     * @throws RuntimeException 요청 실패 시 발생
     */
    public List<ApplicantsResponse> getApplicantsList(Long recruitSeq) {
        URI uri = getUri(POSITION_URI + "/" + recruitSeq + "/applicants");

        ResponseEntity<List<ApplicantsResponse>> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<ApplicantsResponse>>() {});

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Applicants Members List. status:{}, recruitSeq:{}", responseEntity.getStatusCode(), recruitSeq);
            throw new RuntimeException("Error from getting Applicants Members List. " + responseEntity.getStatusCode() + ", " + recruitSeq);
        }

        return responseEntity.getBody();
    }

    /**
     * 해당 공고 팀원 목록
     * @param recruitSeq 공고글 시퀀스
     * @return 팀원 정보 목록 응답
     * @throws RuntimeException 요청 실패 시 발생
     */
    public List<RecruitCrewResponse> getRecruitCrewList(Long recruitSeq) {
        URI uri = getUri(POSITION_URI + "/" + recruitSeq + "/crews");

        ResponseEntity<List<RecruitCrewResponse>> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<RecruitCrewResponse>>() {});

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error from getting Recruit Crew List. status:{}, recruitSeq:{}", responseEntity.getStatusCode(), recruitSeq);
            throw new RuntimeException("Error from getting Recruit Crew List. " + responseEntity.getStatusCode() + ", " + recruitSeq);
        }

        return responseEntity.getBody();
    }

    public URI getUri(String path) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromPath(path)
                .scheme("http")
                .host(projectProperties.getHost())
                .port(projectProperties.getPort());

        return uriComponentsBuilder.build().encode().toUri();
    }
}
