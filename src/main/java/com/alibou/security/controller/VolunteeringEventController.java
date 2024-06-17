package com.alibou.security.controller;

import com.alibou.security.dtos.UserEventDto;
import com.alibou.security.dtos.VolunteeringEventDto;
import com.alibou.security.entities.VolunteeringEvent;
import com.alibou.security.service.VolunteeringEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class VolunteeringEventController {

    private final VolunteeringEventService volunteeringEventService;

    @PostMapping
    public ResponseEntity<VolunteeringEventDto> saveEvent(@RequestBody @Valid VolunteeringEventDto dto) {
        VolunteeringEvent event = volunteeringEventService.save(dto);
        return new ResponseEntity<>(new VolunteeringEventDto(event), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        volunteeringEventService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-organisation/{organisationId}")
    public ResponseEntity<List<VolunteeringEventDto>> getEventByOrganisation(@PathVariable Integer organisationId) {
        List<VolunteeringEvent> events = volunteeringEventService.findByOrganisationId(organisationId);
        return ResponseEntity.ok(events.stream().map(VolunteeringEventDto::new).toList());
    }

    @PostMapping("/add-volunteer")
    public ResponseEntity<Void> addVolunteer(@RequestBody UserEventDto dto) {
        volunteeringEventService.addVolunteer(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-request")
    public ResponseEntity<Void> addRequest(@RequestBody Integer eventId,
                                           Principal connectedUser) {
        volunteeringEventService.addRequest(eventId, connectedUser.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject-request")
    public ResponseEntity<Void> rejectRequest(@RequestBody Integer requestId) {
        volunteeringEventService.rejectRequest(requestId);
        return ResponseEntity.ok().build();
    }
}
