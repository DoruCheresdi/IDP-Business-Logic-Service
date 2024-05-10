package com.alibou.security.dtos;

import com.alibou.security.entities.Address;
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

    public AddressReturnDto(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.postalCode = address.getPostalCode();
        this.country = address.getCountry();
    }
}
