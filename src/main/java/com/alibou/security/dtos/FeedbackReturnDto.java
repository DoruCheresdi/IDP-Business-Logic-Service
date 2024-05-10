package com.alibou.security.dtos;

import com.alibou.security.entities.Feedback;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackReturnDto {

    private Integer id;

    private String comments;

    private String satisfactionLevelSelect;

    private String expectationRadioButton;

    private String improvementsCheckbox;

    public FeedbackReturnDto(Feedback feedback) {
        this.id = feedback.getId();
        this.comments = feedback.getComments();
        this.satisfactionLevelSelect = feedback.getSatisfactionLevelSelect();
        this.expectationRadioButton = feedback.getExpectationRadioButton();
        this.improvementsCheckbox = feedback.getImprovementsCheckbox();
    }
}
