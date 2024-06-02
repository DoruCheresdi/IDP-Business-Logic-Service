package com.alibou.security.service;

import com.alibou.security.auth.AuthenticationService;
import com.alibou.security.dtos.*;
import com.alibou.security.entities.Address;
import com.alibou.security.entities.Feedback;
import com.alibou.security.entities.Organisation;
import com.alibou.security.entities.User;
import com.alibou.security.repository.AddressRepository;
import com.alibou.security.repository.FeedbackRepository;
import com.alibou.security.repository.OrganisationRepository;
import com.alibou.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class OrganisationService {

    private final OrganisationRepository organisationRepository;

    private final UserRepository userRepository;

    private final AuthenticationService authenticationService;
    private final AddressRepository addressRepository;

    public Organisation save(OrganisationDto dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));

        // new feedback:
        var organisation = Organisation.builder()
                .name(dto.getName())
                .owner(user)
                .iban(dto.getIban())
                .description(dto.getDescription())
                .isApproved(false)
                .isFeatured(false).build();

        // TODO commented this since it is annoying to have to reauthenticate from swagger:
//        authenticationService.revokeAllUserTokens(user);

        return organisationRepository.save(organisation);
    }

    public Organisation findById(Integer id) {
        return organisationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource"));
    }

    public Page<Organisation> findAllPaged(Pageable pageable) {
        return organisationRepository.findAll(pageable);
    }

    public List<Organisation> findAll() {
        return organisationRepository.findAll();
    }

    public void deleteById(Integer id) {
        Organisation organisation = findById(id);
        if (organisation == null) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find organisation");
        }

        organisationRepository.delete(organisation);
    }

    public void addAsVolunteer(String userEmail, Integer organisationId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));
        Organisation organisation = findById(organisationId);
        if (organisation == null) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find organisation");
        }
        organisation.getVolunteers().add(user);
        organisationRepository.save(organisation);
    }

    public Organisation update(OrganisationUpdateDto dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));

        Organisation organisation = findById(dto.getId());
        if (organisation == null) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find organisation");
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
            throw new ResponseStatusException(NOT_FOUND, "Unable to find organisation");
        }

        return organisation.getVolunteers();
    }

    public Organisation addAddressToOrganisation(Integer organisationId, Address address) {
        Organisation organisation = organisationRepository.findById(organisationId)
                .orElseThrow(() -> new RuntimeException("Organisation not found"));

        address.setOrganisation(organisation);
        addressRepository.save(address);

        organisation.getAddresses().add(address);
        return organisationRepository.save(organisation);
    }

    public void approveOrganisation(OrganisationApprovalDto dto) {
        Organisation organisation = organisationRepository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find organisation"));

        organisation.setIsApproved(dto.getIsApproved());
        organisationRepository.save(organisation);
    }

    public void featureOrganisation(OrganisationFeaturedDto dto) {
        Organisation organisation = organisationRepository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find organisation"));

        organisation.setIsFeatured(dto.getIsFeatured());
        organisationRepository.save(organisation);
    }

    public List<Organisation> findAllFeatured() {
        return organisationRepository.findAllByIsFeatured(true);
    }
}
