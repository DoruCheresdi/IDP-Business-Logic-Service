package com.alibou.security.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReturnDto {

    private String firstname;

    private String lastname;

    private String email;
}
