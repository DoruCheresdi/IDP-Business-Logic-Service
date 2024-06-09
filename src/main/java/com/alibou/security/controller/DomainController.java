package com.alibou.security.controller;


import com.alibou.security.dtos.DomainDto;
import com.alibou.security.dtos.DomainOrgReqDto;
import com.alibou.security.dtos.DomainUserReqDto;
import com.alibou.security.service.DomainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/domain")
@RequiredArgsConstructor
public class DomainController {

    public final DomainService domainService;

    @PostMapping
    public ResponseEntity<DomainDto> saveDomain(@RequestBody @Valid DomainDto dto) {
        return new ResponseEntity<>(new DomainDto(domainService.save(dto)), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DomainDto>> findAllDomains() {
        return ResponseEntity.ok(domainService.findAll().stream().map(DomainDto::new).toList());
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addUserToDomain(@RequestBody @Valid DomainUserReqDto dto) {
        domainService.addUserToDomain(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-org")
    public ResponseEntity<?> addOrganisationToDomain(@RequestBody @Valid DomainOrgReqDto dto) {
        domainService.addOrganisationToDomain(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove-user")
    public ResponseEntity<?> removeUserFromDomain(@RequestBody @Valid DomainUserReqDto dto) {
        domainService.removeUserFromDomain(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove-org")
    public ResponseEntity<?> removeOrganisationFromDomain(@RequestBody @Valid DomainOrgReqDto dto) {
        domainService.removeOrganisationFromDomain(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-org")
    public ResponseEntity<List<DomainDto>> findDomainsByOrganisation(@RequestBody Integer orgId) {
        return ResponseEntity.ok(domainService.findByOrganisation(orgId).stream().map(DomainDto::new).toList());
    }
}
