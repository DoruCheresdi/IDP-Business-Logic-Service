package com.alibou.security.service;

import com.alibou.security.dtos.AddressDto;
import com.alibou.security.dtos.AddressReturnDto;
import com.alibou.security.dtos.OrganisationDto;
import com.alibou.security.dtos.OrganisationUpdateDto;
import com.alibou.security.entities.Organisation;
import com.alibou.security.entities.User;
import com.alibou.security.repository.OrganisationRepository;
import com.alibou.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class OrganisationService {

    @Value("${dbServiceUrl}")
    private String dbServiceUrl;
    private RestTemplate restTemplate = new RestTemplate();

    private final OrganisationRepository organisationRepository;

    private final UserRepository userRepository;

    public Organisation save(OrganisationDto dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Unable to find user"));

        // new feedback:
        var organisation = Organisation.builder()
                .name(dto.getName())
                .owner(user)
                .iban(dto.getIban())
                .description(dto.getDescription()).build();

        // TODO commented this since it is annoying to have to reauthenticate from swagger:
//        authenticationService.revokeAllUserTokens(user);

        return organisationRepository.save(organisation);
    }

    public Organisation findById(Integer id) {
        return organisationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Unable to find resource"));
    }

    public Page<Organisation> findAllPaged(Pageable pageable) {
        return organisationRepository.findAll(pageable);
    }

    public void deleteById(Integer id) {
        Organisation organisation = findById(id);
        if (organisation == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Unable to find organisation");
        }

        organisationRepository.delete(organisation);
    }

    public void addAsVolunteer(String userEmail, Integer organisationId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Unable to find user"));
        Organisation organisation = findById(organisationId);
        if (organisation == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Unable to find organisation");
        }
        organisation.getVolunteers().add(user);
        organisationRepository.save(organisation);
    }

    public Organisation update(OrganisationUpdateDto dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Unable to find user"));

        Organisation organisation = findById(dto.getId());
        if (organisation == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Unable to find organisation");
        }

        if (organisation.getOwner().getId() != user.getId()) {
            throw new ResponseStatusException(UNAUTHORIZED, "You can't update a company you don't own!");
        }

        organisation.setDescription(dto.getDescription());
        organisation.setName(dto.getName());
        organisation.setIban(dto.getIban());
        return organisationRepository.save(organisation);
    }

    public List<User> findAllVolunteers(Integer organisationId) {
        Organisation organisation = findById(organisationId);
        if (organisation == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Unable to find organisation");
        }

        return organisation.getVolunteers();
    }

    public AddressReturnDto addAddressToOrganisation(Integer organisationId, AddressDto addressDto) {
        Map<String, Integer> params = new HashMap<>();
        params.put("organisationId", organisationId);
        return restTemplate.postForObject(dbServiceUrl + "/organisation/add-address", addressDto, AddressReturnDto.class, params);
    }

}
