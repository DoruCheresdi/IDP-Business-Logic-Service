package com.alibou.security.repository;

import com.alibou.security.entities.Organisation;
import com.alibou.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Integer>, PagingAndSortingRepository<Organisation, Integer> {

    public List<Organisation> findAllByOwner(User owner);
}
