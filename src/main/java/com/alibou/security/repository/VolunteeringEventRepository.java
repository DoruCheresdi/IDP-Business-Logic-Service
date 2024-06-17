package com.alibou.security.repository;

import com.alibou.security.entities.Organisation;
import com.alibou.security.entities.VolunteeringEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VolunteeringEventRepository extends JpaRepository<VolunteeringEvent, Integer>, PagingAndSortingRepository<VolunteeringEvent, Integer> {

    List<VolunteeringEvent> findAllByOrganisation(Organisation organisation);
}
