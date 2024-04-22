package com.alibou.security.controller;

import com.alibou.security.dtos.ReviewDto;
import com.alibou.security.entities.Review;
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

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> saveReview(@RequestBody @Valid ReviewDto dto, Principal connectedUser) {
        Review review = reviewService.save(dto, connectedUser.getName());
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable Integer id) {
        Review review = reviewService.findById(id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Review>> findAllReviewsPaged(@Valid Pageable pageable) {
        return ResponseEntity.ok(reviewService.findAllPaged(pageable));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        reviewService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-organisation")
    public ResponseEntity<List<Review>> findAllReviewsByOrganisation(@NotNull Integer organisationId) {
        List<Review> reviews = reviewService.findAllByOrganisationId(organisationId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<Review>> findAllReviewsByUser(@RequestParam String userEmail) {
        List<Review> reviews = reviewService.findAllByUserEmail(userEmail);
        return ResponseEntity.ok(reviews);
    }
}
