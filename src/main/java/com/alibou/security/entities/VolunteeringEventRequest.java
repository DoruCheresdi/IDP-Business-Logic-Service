package com.alibou.security.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "volunteering_event_request")
public class VolunteeringEventRequest {

    @Id
    @GeneratedValue
    private Integer id;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "event_id"
    )
    private VolunteeringEvent volunteeringEvent;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id"
    )
    private User volunteer;

    private String status;

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
