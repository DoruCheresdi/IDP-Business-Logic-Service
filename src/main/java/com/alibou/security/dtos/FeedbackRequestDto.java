package com.alibou.security.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequestDto {

    @NotNull
    private String comments;

    @NotNull
    private String satisfactionLevelSelect;

    @NotNull
    private String expectationRadioButton;

    @NotNull
    private String improvementsCheckbox;

    @Nullable
    private String userEmail;
}
