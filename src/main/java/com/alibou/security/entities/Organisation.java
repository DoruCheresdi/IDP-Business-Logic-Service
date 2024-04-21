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
@Table(name = "organisation")
public class Organisation {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisations")
    private List<User> users;

}
