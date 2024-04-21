package com.alibou.security.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrganisationDto {

    @NotNull
    private String name;

    @NotNull
    private String iban;

    @NotNull
    private String description;
}
