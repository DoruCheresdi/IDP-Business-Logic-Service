package com.alibou.security.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationReturnDto {

    private Integer id;

    private String name;

    private String iban;

    private String description;

    private List<UserReturnDto> volunteers;
}
