package com.alibou.security.service;

import com.alibou.security.dtos.UserEventDto;
import com.alibou.security.dtos.VolunteeringEventDto;
import com.alibou.security.entities.Organisation;
import com.alibou.security.entities.User;
import com.alibou.security.entities.VolunteeringEvent;
import com.alibou.security.repository.OrganisationRepository;
import com.alibou.security.repository.UserRepository;
import com.alibou.security.repository.VolunteeringEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class VolunteeringEventService {

    private final OrganisationRepository organisationRepository;

    private final UserRepository userRepository;

    private final VolunteeringEventRepository volunteeringEventRepository;

    public VolunteeringEvent save(VolunteeringEventDto dto) {
        Organisation organisation = organisationRepository.findById(dto.getOrganisationId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Unable to find organisation"));

        var event = VolunteeringEvent.builder()
                .name(dto.getName())
                .date(dto.getDate())
                .hours(dto.getHours())
                .location(dto.getLocation())
                .organisation(organisation)
                .build();

        return volunteeringEventRepository.save(event);
    }

    public void deleteById(Integer id) {
        volunteeringEventRepository.deleteById(id);
    }

    public List<VolunteeringEvent> findByOrganisationId(Integer organisationId) {
        Organisation organisation = organisationRepository.findById(organisationId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Unable to find organisation"));
        return volunteeringEventRepository.findAllByOrganisation(organisation);
    }

    public void addVolunteer(UserEventDto dto) {
        VolunteeringEvent event = volunteeringEventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Unable to find event"));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Unable to find user"));
        event.getVolunteers().add(user);
        user.getEventsVolunteered().add(event);

        volunteeringEventRepository.save(event);
    }
}
