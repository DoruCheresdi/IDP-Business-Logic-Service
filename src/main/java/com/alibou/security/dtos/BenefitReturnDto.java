package com.alibou.security.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenefitReturnDto {

    private Integer id;

    private Double priceInLei;

    private String name;
}
