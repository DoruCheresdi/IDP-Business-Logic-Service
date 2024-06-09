package com.alibou.security.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainOrgReqDto {

    @NotNull
    private Integer orgId;

    @NotNull
    private Integer domainId;
}
