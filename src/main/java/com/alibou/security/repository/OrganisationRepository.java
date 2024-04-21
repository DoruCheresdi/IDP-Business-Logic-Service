package com.alibou.security.repository;

import com.alibou.security.entities.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrganisationRepository extends JpaRepository<Organisation, Integer>, PagingAndSortingRepository<Organisation, Integer> {
}
