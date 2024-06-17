package com.alibou.security.dtos;

import com.alibou.security.entities.VolunteeringEventRequest;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteeringEventRequestDto {

        private Integer id;

        private Integer eventId;

        private Integer userId;

        private String status;

        public VolunteeringEventRequestDto(VolunteeringEventRequest request) {
            this.id = request.getId();
            this.eventId = request.getVolunteeringEvent().getId();
            this.userId = request.getVolunteer().getId();
            this.status = request.getStatus();
        }
}
