package com.alibou.security.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BenefitDto {

    @NotNull
    private Double priceInLei;

    @NotNull
    private String name;

    @NotNull
    private Integer organisationId;
}
