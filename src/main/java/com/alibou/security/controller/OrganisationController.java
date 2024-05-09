package com.alibou.security.controller;

import com.alibou.security.dtos.*;
import com.alibou.security.entities.Address;
import com.alibou.security.entities.Organisation;
import com.alibou.security.entities.User;
import com.alibou.security.service.OrganisationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/organisation")
@RequiredArgsConstructor
public class OrganisationController {

    private final OrganisationService organisationService;

    @PostMapping
    public ResponseEntity<OrganisationReturnDto> saveOrganisation(@RequestBody @Valid OrganisationDto dto,
                                                                  Principal connectedUser) {
        Organisation organisation = organisationService.save(dto, connectedUser.getName());
        return new ResponseEntity<>(new OrganisationReturnDto(organisation), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganisationReturnDto> getOrganisation(@PathVariable @NotNull Integer id) {
        Organisation organisation = organisationService.findById(id);
        return new ResponseEntity<>(new OrganisationReturnDto(organisation), HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<OrganisationReturnDto>> findAllOrganisationsPaged(@ParameterObject @Valid Pageable pageable) {
        return ResponseEntity.ok(organisationService.findAllPaged(pageable).map(OrganisationReturnDto::new));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrganisation(@PathVariable @NotNull Integer id) {
        organisationService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-volunteer")
    public ResponseEntity<?> addAsVolunteer(Principal connectedUser, @NotNull Integer organisationId) {
        organisationService.addAsVolunteer(connectedUser.getName(), organisationId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<OrganisationReturnDto> updateOrganisation(@RequestBody @Valid OrganisationUpdateDto dto,
                                                                    Principal connectedUser) {
        Organisation organisation = organisationService.update(dto, connectedUser.getName());
        return ResponseEntity.ok(new OrganisationReturnDto(organisation));
    }

    @GetMapping("/get-all-volunteers/{organisationId}")
    public ResponseEntity<List<UserReturnDto>> getAllVolunteers(@PathVariable @NotNull Integer organisationId) {
        List<User> volunteers = organisationService.findAllVolunteers(organisationId);
        List<UserReturnDto> dtos = volunteers.stream()
                .map(UserReturnDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/{organisationId}/add-address")
    public ResponseEntity<AddressReturnDto> addAddressToOrganisation(
            @PathVariable @NotNull Integer organisationId,
            @RequestBody @Valid AddressDto addressDto
    ) {
        Address address = Address.builder()
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .postalCode(addressDto.getPostalCode())
                .country(addressDto.getCountry())
                .build();

        Address savedAddress = organisationService.addAddressToOrganisation(organisationId, address).getAddresses().stream()
                .filter(a -> a.getStreet().equals(address.getStreet())) // assuming street is unique to identify the address
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Address not found after saving"));

        return new ResponseEntity<>(new AddressReturnDto(savedAddress), HttpStatus.CREATED);
    }
}
