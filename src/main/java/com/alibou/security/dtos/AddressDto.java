package com.alibou.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AddressDto {

    private String street;

    private String city;

    private String postalCode;

    private String country;
}
