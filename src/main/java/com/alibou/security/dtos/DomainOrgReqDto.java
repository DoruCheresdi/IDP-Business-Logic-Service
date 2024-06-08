package com.alibou.security.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainOrgReqDto {

    private Integer orgId;

    private Integer domainId;
}
