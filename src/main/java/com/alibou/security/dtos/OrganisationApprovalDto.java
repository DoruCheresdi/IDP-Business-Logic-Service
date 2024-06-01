package com.alibou.security.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationApprovalDto {

    private Integer id;
    private Boolean isApproved;
}
