package com.alibou.security.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue
    private Integer id;

    private String comments;

    private String satisfactionLevelSelect;

    private String expectationRadioButton;

    private String improvementsCheckbox;


    @JsonIgnore
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedback_id")
    private User user;
}
