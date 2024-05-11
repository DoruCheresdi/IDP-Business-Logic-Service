package com.alibou.security.controller;

import com.alibou.security.dtos.ReviewDto;
import com.alibou.security.dtos.ReviewReturnDto;
import com.alibou.security.service.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewReturnDto> saveReview(@RequestBody @Valid ReviewDto dto, Principal connectedUser) {
        ReviewReturnDto review = reviewService.save(dto, connectedUser.getName());
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewReturnDto> getReview(@PathVariable Integer id) {
        ReviewReturnDto review = reviewService.findById(id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<ReviewReturnDto>> findAllReviewsPaged(Pageable pageable) {
        Page<ReviewReturnDto> reviews = reviewService.findAllPaged(pageable);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        reviewService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-organisation")
    public ResponseEntity<List<ReviewReturnDto>> findAllReviewsByOrganisation(@NotNull Integer organisationId) {
        List<ReviewReturnDto> reviews = reviewService.findAllByOrganisationId(organisationId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<ReviewReturnDto>> findAllReviewsByUser(@RequestParam String userEmail) {
        List<ReviewReturnDto> reviews = reviewService.findAllByUserEmail(userEmail);
        return ResponseEntity.ok(reviews);
    }
}
