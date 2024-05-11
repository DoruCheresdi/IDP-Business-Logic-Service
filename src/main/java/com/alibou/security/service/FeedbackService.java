package com.alibou.security.service;

import com.alibou.security.dtos.CustomPageImpl;
import com.alibou.security.dtos.FeedbackRequestDto;
import com.alibou.security.dtos.FeedbackReturnDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    @Value("${dbServiceUrl}")
    private String dbServiceUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public FeedbackReturnDto save(FeedbackRequestDto dto, String userEmail) {
        dto.setUserEmail(userEmail);
        return restTemplate.postForObject(dbServiceUrl + "/feedback", dto, FeedbackReturnDto.class);
    }

    public FeedbackReturnDto update(FeedbackRequestDto dto, String userEmail) {
        dto.setUserEmail(userEmail);
        HttpEntity<FeedbackRequestDto> request = new HttpEntity<>(dto);
        return restTemplate.exchange(
                dbServiceUrl + "/feedback",
                HttpMethod.PUT,
                request,
                FeedbackReturnDto.class).getBody();
    }

    public FeedbackReturnDto findById(Integer id) {
        return restTemplate.getForObject(dbServiceUrl + "/feedback/" + id, FeedbackReturnDto.class);
    }

    public List<FeedbackReturnDto> findAll() {
        return restTemplate.exchange(
                dbServiceUrl + "/feedback/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FeedbackReturnDto>>() {}).getBody();
    }

    public Page<FeedbackReturnDto> findAllPaged(Pageable pageable) {
        return restTemplate.exchange(
                dbServiceUrl + "/feedback/paged?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CustomPageImpl<FeedbackReturnDto>>() {}).getBody();
    }

    public void deleteById(Integer id) {
        restTemplate.delete(dbServiceUrl + "/feedback/" + id);
    }
}
