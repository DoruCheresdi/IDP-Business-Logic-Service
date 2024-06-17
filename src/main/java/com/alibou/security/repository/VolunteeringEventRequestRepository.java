package com.alibou.security.repository;

import com.alibou.security.entities.User;
import com.alibou.security.entities.VolunteeringEvent;
import com.alibou.security.entities.VolunteeringEventRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VolunteeringEventRequestRepository extends JpaRepository<VolunteeringEventRequest, Integer>,
        PagingAndSortingRepository<VolunteeringEventRequest, Integer> {

    VolunteeringEventRequest findByVolunteeringEventAndVolunteer(VolunteeringEvent event, User volunteer);
}
