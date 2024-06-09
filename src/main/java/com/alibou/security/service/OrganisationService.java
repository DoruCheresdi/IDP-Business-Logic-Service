package com.alibou.security.service;

import com.alibou.security.auth.AuthenticationService;
import com.alibou.security.dtos.OrganisationApprovalDto;
import com.alibou.security.dtos.OrganisationDto;
import com.alibou.security.dtos.OrganisationFeaturedDto;
import com.alibou.security.dtos.OrganisationUpdateDto;
import com.alibou.security.entities.*;
import com.alibou.security.repository.AddressRepository;
import com.alibou.security.repository.OrganisationRepository;
import com.alibou.security.repository.UserRepository;
import com.alibou.security.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Comparator;
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
                .orgLink(dto.getOrgLink())
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

    public List<Organisation> findAllOrdered(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));
        return organisationRepository.findAll().stream()
                .sorted(compareOrgsBasedOnDomainsAndAverageRatings(user))
                .toList();
    }

    private Comparator<Organisation> compareOrgsBasedOnDomainsAndAverageRatings(User user) {
        return (o1, o2) -> {
            Integer domainCompareResult = compareDomains(user, o1, o2);
            if (domainCompareResult != null) return domainCompareResult;
            return compareAverageRatings(o1, o2);
        };
    }

    private int compareAverageRatings(Organisation o1, Organisation o2) {
        double o1AverageRating = getOrgAverageRating(o1);
        double o2AverageRating = getOrgAverageRating(o2);
        return Double.compare(o2AverageRating, o1AverageRating);
    }

    private double getOrgAverageRating(Organisation o1) {
        return o1.getReviews().stream().mapToDouble(Review::getStars).average().orElse(0.0);
    }

    private static Integer compareDomains(User user, Organisation o1, Organisation o2) {
        List<ActivityDomain> userDomains = user.getDomains();
        boolean o1HasDomain = o1.getDomains().stream().anyMatch(userDomains::contains);
        boolean o2HasDomain = o2.getDomains().stream().anyMatch(userDomains::contains);
        if (o1HasDomain && !o2HasDomain) {
            return -1;
        } else if (!o1HasDomain && o2HasDomain) {
            return 1;
        }
        return null;
    }

    public void deleteById(Integer id) {
        Organisation organisation = findById(id);
        if (organisation == null) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find organisation");
        }

        organisationRepository.delete(organisation);
    }

    public void addAsFavorite(String userEmail, Integer organisationId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));
        Organisation organisation = findById(organisationId);
        if (organisation == null) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find organisation");
        }
        organisation.getUsersThatFavorited().add(user);
        organisationRepository.save(organisation);
    }

    public void removeAsFavorite(String userEmail, Integer organisationId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));
        Organisation organisation = findById(organisationId);
        if (organisation == null) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find organisation");
        }
        organisation.getUsersThatFavorited().remove(user);
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

    public List<User> findAllFavoriters(Integer organisationId) {
        Organisation organisation = findById(organisationId);
        if (organisation == null) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find organisation");
        }

        return organisation.getUsersThatFavorited();
    }

    public List<Organisation> findAllFavoriteOrganisations(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));

        return user.getFavoritedOrganisations();
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

    public void saveOrganisationPicture(MultipartFile multipartFile, Integer organisationId) throws IOException {
        if (multipartFile.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "Please select a file to upload");
        }
        if (multipartFile.getOriginalFilename() == null) {
            throw new ResponseStatusException(BAD_REQUEST, "File has no name");
        }
        Organisation organisation = organisationRepository.findById(organisationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find organisation"));

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        String uploadDir = "assets/org-pictures/" + organisationId;

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        // save image name to organisation:
        organisation.setPicture(fileName);
        organisationRepository.save(organisation);
    }
}
