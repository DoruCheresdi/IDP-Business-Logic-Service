package com.alibou.security.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainUserReqDto {

    @NotNull
    private String userEmail;

    @NotNull
    private Integer domainId;
}
