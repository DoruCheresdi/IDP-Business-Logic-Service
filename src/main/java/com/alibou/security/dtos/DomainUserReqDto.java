package com.alibou.security.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainUserReqDto {

    private String userEmail;

    private Integer domainId;
}
