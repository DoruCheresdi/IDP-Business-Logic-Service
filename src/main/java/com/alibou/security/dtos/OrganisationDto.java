package com.alibou.security.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationDto {

    @NotNull
    private String name;

    @NotNull
    private String iban;

    @NotNull
    private String description;
}
