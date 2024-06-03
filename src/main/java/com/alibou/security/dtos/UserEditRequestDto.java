package com.alibou.security.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEditRequestDto {

    private String email;
    private String firstName;
    private String lastName;
}
