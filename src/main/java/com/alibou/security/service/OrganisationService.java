package com.alibou.security.service;

import com.alibou.security.dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class OrganisationService {

    @Value("${dbServiceUrl}")
    private String dbServiceUrl;
    private RestTemplate restTemplate = new RestTemplate();


    public OrganisationReturnDto save(OrganisationDto dto, String userEmail) {
        dto.setUserEmail(userEmail);
        return restTemplate.postForObject(dbServiceUrl + "/organisation", dto,  OrganisationReturnDto.class);
    }

    public OrganisationReturnDto findById(Integer id) {
        return restTemplate.getForObject(dbServiceUrl + "/organisation/" + id, OrganisationReturnDto.class);
    }

    public void deleteById(Integer id) {
        restTemplate.delete(dbServiceUrl + "/organisation/" + id);
    }

    public Page<OrganisationReturnDto> findAllPaged(Pageable pageable) {

        return restTemplate.exchange(
                dbServiceUrl + "/organisation/paged?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CustomPageImpl<OrganisationReturnDto>>() {}).getBody();
    }

    public void addAsVolunteer(String userEmail, Integer organisationId) {
        AddVolunteerDto dto = new AddVolunteerDto();
        dto.setOrganisationId(organisationId);
        dto.setUserEmail(userEmail);
        restTemplate.postForLocation(dbServiceUrl + "/organisation/add-volunteer", dto);
    }

    public OrganisationReturnDto update(OrganisationUpdateDto dto, String userEmail) {
        dto.setUserEmail(userEmail);
        HttpEntity<OrganisationUpdateDto> request = new HttpEntity<>(dto);
        return restTemplate.exchange(
                dbServiceUrl + "/organisation",
                HttpMethod.PUT,
                request,
                OrganisationReturnDto.class).getBody();
    }

    public List<UserReturnDto> findAllVolunteers(Integer organisationId) {
        return restTemplate.exchange(
                dbServiceUrl + "/organisation/get-all-volunteers/" + organisationId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserReturnDto>>() {}).getBody();
    }

    public AddressReturnDto addAddressToOrganisation(Integer organisationId, AddressDto addressDto) {
        Map<String, Integer> params = new HashMap<>();
        params.put("organisationId", organisationId);
        return restTemplate.postForObject(dbServiceUrl + "/organisation/{organisationId}/add-address", addressDto, AddressReturnDto.class, params);
    }

}
