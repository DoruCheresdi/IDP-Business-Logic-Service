package com.alibou.security.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "benefit")
public class Benefit {

    @Id
    @GeneratedValue
    private Integer id;

    private Double priceInLei;

    private String name;

    private String subunitaryDescription;

    private String superunitaryDescription;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "organisation_id"
    )
    private Organisation organisation;
}
