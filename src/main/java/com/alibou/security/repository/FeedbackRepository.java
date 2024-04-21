package com.alibou.security.repository;

import com.alibou.security.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer>, PagingAndSortingRepository<Feedback, Integer> {
}
