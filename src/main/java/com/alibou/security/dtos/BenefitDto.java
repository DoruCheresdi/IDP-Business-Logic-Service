package com.alibou.security.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenefitDto {

    @NotNull
    private Double priceInLei;

    @NotNull
    private String name;

    @NotNull
    private Integer organisationId;

    @Nullable
    private String userEmail;
}
