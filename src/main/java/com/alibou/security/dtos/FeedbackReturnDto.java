package com.alibou.security.dtos;

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
}
