package com.alibou.security.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BenefitDto {

    private Double priceInLei;

    private String name;

    private Integer organisationId;
}
