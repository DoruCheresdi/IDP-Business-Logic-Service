package com.alibou.security.service;

import com.alibou.security.dtos.BenefitDto;
import com.alibou.security.dtos.BenefitReturnDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BenefitService {

    @Value("${dbServiceUrl}")
    private String dbServiceUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public BenefitReturnDto save(BenefitDto dto, String userEmail) {
        dto.setUserEmail(userEmail);
        return restTemplate.postForObject(dbServiceUrl + "/benefit", dto,  BenefitReturnDto.class);
    }

    public BenefitReturnDto findById(Integer id) {
        return restTemplate.getForObject(dbServiceUrl + "/benefit/" + id, BenefitReturnDto.class);
    }

    public void deleteById(Integer id) {
        restTemplate.delete(dbServiceUrl + "/benefit/" + id);
    }

    public List<BenefitReturnDto> findAllBenefitsForOrganisation(Integer organisationId) {
        return restTemplate.exchange(
                dbServiceUrl + "/benefit/get-all-benefits?organisationId=" + organisationId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BenefitReturnDto>>() {}).getBody();
    }
}
