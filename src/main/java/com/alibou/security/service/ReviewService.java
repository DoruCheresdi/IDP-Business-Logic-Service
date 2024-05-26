package com.alibou.security.service;

import com.alibou.security.dtos.ReviewDto;
import com.alibou.security.dtos.ReviewUpdateDto;
import com.alibou.security.entities.Organisation;
import com.alibou.security.entities.Review;
import com.alibou.security.entities.User;
import com.alibou.security.repository.OrganisationRepository;
import com.alibou.security.repository.ReviewRepository;
import com.alibou.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrganisationRepository organisationRepository;
    private final UserRepository userRepository;

    public Review save(ReviewDto dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));

        Organisation organisation = organisationRepository.findById(dto.getOrganisationId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find organisation"));

        var review = Review.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .stars(dto.getStars())
                .reviewer(user)
                .organisationReviewed(organisation)
                .build();

        return reviewRepository.save(review);
    }

    public Review findById(Integer id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find review"));
    }

    public Page<Review> findAllPaged(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public void deleteById(Integer id) {
        Review review = findById(id);
        reviewRepository.delete(review);
    }

    public List<Review> findAllByOrganisationId(Integer organisationId) {
        Organisation organisation = organisationRepository.findById(organisationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find organisation"));

        return reviewRepository.findAllByOrganisationReviewed(organisation);
    }

    public List<Review> findAllByUserEmail(String userEmail) {
        return reviewRepository.findAllByReviewerEmail(userEmail);
    }

    public Review update(ReviewUpdateDto dto, String userEmail) {
        Review review = reviewRepository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find review"));

        if (!review.getReviewer().getEmail().equals(userEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this review");
        }

        review.setTitle(dto.getTitle());
        review.setDescription(dto.getDescription());
        review.setStars(dto.getStars());

        return reviewRepository.save(review);
    }
}
