package com.alibou.security.dtos;

import com.alibou.security.entities.VolunteeringEvent;
import com.alibou.security.entities.VolunteeringEventRequest;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteeringEventDto {

    private Integer id;

    private String name;

    private Date date;

    private String hours;

    private String location;

    private Integer organisationId;

    private List<UserReturnDto> volunteers;

    private List<VolunteeringEventRequest> requests;

    public VolunteeringEventDto(VolunteeringEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.date = event.getDate();
        this.hours = event.getHours();
        this.location = event.getLocation();
        this.organisationId = event.getOrganisation().getId();
        if (event.getVolunteers() != null) {
            this.volunteers = event.getVolunteers().stream().map(UserReturnDto::new).toList();
        }
        if (event.getRequests() != null) {
            this.requests = event.getRequests();
        }
    }
}
