package com.alibou.security.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FeedbackRequestDto {

    @NotNull
    private String comments;

    @NotNull
    private String satisfactionLevelSelect;

    @NotNull
    private String expectationRadioButton;

    @NotNull
    private String improvementsCheckbox;
}
