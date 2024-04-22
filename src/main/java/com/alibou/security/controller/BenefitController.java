package com.alibou.security.controller;

import com.alibou.security.dtos.BenefitDto;
import com.alibou.security.entities.Benefit;
import com.alibou.security.service.BenefitService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/benefit")
@RequiredArgsConstructor
public class BenefitController {

    private final BenefitService benefitService;

    @PostMapping
    public ResponseEntity<Benefit> saveBenefit(@RequestBody @Valid BenefitDto dto) {
        Benefit benefit = benefitService.save(dto);
        return new ResponseEntity<>(benefit, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Benefit> getBenefit(@NotNull Integer id) {
        Benefit benefit = benefitService.findById(id);
        return new ResponseEntity<>(benefit, HttpStatus.OK);
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteBenefit(@NotNull Integer id) {
        benefitService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-all-benefits")
    public ResponseEntity<List<Benefit>> getAllBenefitsForOrganisations(@NotNull Integer organisationId) {
        List<Benefit> organisations = benefitService.findAllBenefitsForOrganisation(organisationId);
        return ResponseEntity.ok(organisations);
    }
}
