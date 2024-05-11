package com.alibou.security.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddVolunteerDto {

    @NotNull
    private String userEmail;

    @NotNull
    private Integer organisationId;
}
