package com.alibou.security.service;

import com.alibou.security.dtos.CustomPageImpl;
import com.alibou.security.dtos.ReviewDto;
import com.alibou.security.dtos.ReviewReturnDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${dbServiceUrl}")
    private String dbServiceUrl;

    public ReviewReturnDto save(ReviewDto dto, String userEmail) {
        dto.setUserEmail(userEmail);

        return restTemplate
                .postForObject(dbServiceUrl + "/review", dto,  ReviewReturnDto.class);
    }

    public ReviewReturnDto findById(Integer id) {
        return restTemplate.getForObject(dbServiceUrl + "/review/" + id, ReviewReturnDto.class);
    }

    public Page<ReviewReturnDto> findAllPaged(Pageable pageable) {
        return restTemplate.exchange(
                dbServiceUrl + "/review/paged?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CustomPageImpl<ReviewReturnDto>>() {}).getBody();
    }

    public void deleteById(Integer id) {
        restTemplate.delete(dbServiceUrl + "/review/" + id);
    }

    public List<ReviewReturnDto> findAllByOrganisationId(Integer organisationId) {
        return restTemplate.exchange(
                dbServiceUrl + "/review/by-organisation?organisationId=" + organisationId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ReviewReturnDto>>() {}).getBody();
    }

    public List<ReviewReturnDto> findAllByUserEmail(String userEmail) {
        return restTemplate.exchange(
                dbServiceUrl + "/review/by-user?userEmail=" + userEmail,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ReviewReturnDto>>() {}).getBody();
    }
}
