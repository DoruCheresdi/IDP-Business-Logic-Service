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

    private String orgLink;


    @NotNull
    private String description;
}
