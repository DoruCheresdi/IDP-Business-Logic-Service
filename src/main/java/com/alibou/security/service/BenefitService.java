package com.alibou.security.service;

import com.alibou.security.dtos.BenefitDto;
import com.alibou.security.entities.Benefit;
import com.alibou.security.entities.Organisation;
import com.alibou.security.repository.BenefitRepository;
import com.alibou.security.repository.OrganisationRepository;
import com.alibou.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BenefitService {

    private final BenefitRepository benefitRepository;
    private final OrganisationRepository organisationRepository;

    public Benefit save(BenefitDto dto) {
        Organisation organisation = organisationRepository.findById(dto.getOrganisationId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find organisation"));

        var benefit = Benefit.builder()
                .name(dto.getName())
                .priceInLei(dto.getPriceInLei())
                .organisation(organisation)
                .build();

        return benefitRepository.save(benefit);
    }

    public Benefit findById(Integer id) {
        return benefitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource"));
    }

    public void deleteById(Integer id) {
        Benefit benefit = findById(id);
        if (benefit == null) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find benefit");
        }

        benefitRepository.delete(benefit);
    }

    public List<Benefit> findAllBenefitsForOrganisation(Integer organisationId) {
        Organisation organisation = organisationRepository.findById(organisationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find organisation"));

        return benefitRepository.findAllByOrganisation(organisation);
    }
}
