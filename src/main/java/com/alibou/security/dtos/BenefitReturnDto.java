package com.alibou.security.dtos;

import com.alibou.security.entities.Benefit;
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

    private String subunitaryDescription;

    private String superunitaryDescription;

    public BenefitReturnDto(Benefit benefit) {
        this.id = benefit.getId();
        this.priceInLei = benefit.getPriceInLei();
        this.name = benefit.getName();
        this.subunitaryDescription = benefit.getSubunitaryDescription();
        this.superunitaryDescription = benefit.getSuperunitaryDescription();
    }
}
