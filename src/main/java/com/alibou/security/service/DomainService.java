package com.alibou.security.service;

import com.alibou.security.dtos.DomainDto;
import com.alibou.security.dtos.DomainOrgReqDto;
import com.alibou.security.dtos.DomainUserReqDto;
import com.alibou.security.entities.ActivityDomain;
import com.alibou.security.repository.DomainRepository;
import com.alibou.security.repository.OrganisationRepository;
import com.alibou.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DomainService {

    private final OrganisationRepository organisationRepository;

    private final UserRepository userRepository;

    private final DomainRepository domainRepository;

    public ActivityDomain save(DomainDto dto) {
        var domain = ActivityDomain.builder()
                .name(dto.getName())
                .build();

        return domainRepository.save(domain);
    }

    public List<ActivityDomain> findAll() {
        return domainRepository.findAll();
    }

    public void addUserToDomain(DomainUserReqDto dto) {
        var domain = domainRepository.findById(dto.getDomainId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find domain"));

        var user = userRepository.findByEmail(dto.getUserEmail())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));

        domain.getUsers().add(user);
        domainRepository.save(domain);
    }

    public void addOrganisationToDomain(DomainOrgReqDto dto) {
        var domain = domainRepository.findById(dto.getDomainId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find domain"));

        var organisation = organisationRepository.findById(dto.getOrgId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find organisation"));

        domain.getOrganisations().add(organisation);
        domainRepository.save(domain);
    }

    public void removeUserFromDomain(DomainUserReqDto dto) {
        var domain = domainRepository.findById(dto.getDomainId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find domain"));

        var user = userRepository.findByEmail(dto.getUserEmail())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));

        domain.getUsers().remove(user);
        domainRepository.save(domain);
    }

    public void removeOrganisationFromDomain(DomainOrgReqDto dto) {
        var domain = domainRepository.findById(dto.getDomainId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find domain"));

        var organisation = organisationRepository.findById(dto.getOrgId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find organisation"));

        domain.getOrganisations().remove(organisation);
        domainRepository.save(domain);
    }

    public List<ActivityDomain> findByOrganisation(Integer orgId) {
        var organisation = organisationRepository.findById(orgId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find organisation"));
        return domainRepository.findAllByOrganisation(organisation.getId());
    }
}
