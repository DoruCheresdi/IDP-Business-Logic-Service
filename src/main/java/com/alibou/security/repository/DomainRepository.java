package com.alibou.security.repository;

import com.alibou.security.entities.ActivityDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DomainRepository extends JpaRepository<ActivityDomain, Integer>, PagingAndSortingRepository<ActivityDomain, Integer> {

    @Query("SELECT d FROM ActivityDomain d JOIN d.organisations o WHERE o.id = :organisationId")
    public List<ActivityDomain> findAllByOrganisation(@Param("organisationId") Integer organisationId);
}
