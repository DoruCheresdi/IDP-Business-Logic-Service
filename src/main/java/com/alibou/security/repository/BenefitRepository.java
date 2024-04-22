package com.alibou.security.repository;

import com.alibou.security.entities.Benefit;
import com.alibou.security.entities.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BenefitRepository extends JpaRepository<Benefit, Integer>, PagingAndSortingRepository<Benefit, Integer> {

    List<Benefit> findAllByOrganisation(Organisation organisation);
}
