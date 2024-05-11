package com.alibou.security.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressReturnDto {

    private Integer id;

    private String street;

    private String city;

    private String postalCode;

    private String country;

}
