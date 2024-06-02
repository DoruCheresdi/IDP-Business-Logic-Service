package com.alibou.security.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationFeaturedDto {

    private Integer id;
    private Boolean isFeatured;
}
