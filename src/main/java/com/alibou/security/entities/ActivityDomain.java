package com.alibou.security.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activity_domain")
public class ActivityDomain {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "domains")
    private List<Organisation> organisations;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "domains")
    private List<User> users;
}
