package com.alibou.security.repository;

import com.alibou.security.entities.Organisation;
import com.alibou.security.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer>, PagingAndSortingRepository<Review, Integer> {

    List<Review> findAllByOrganisationReviewed(Organisation organisation);

    List<Review> findAllByReviewerEmail(String userEmail);
}
