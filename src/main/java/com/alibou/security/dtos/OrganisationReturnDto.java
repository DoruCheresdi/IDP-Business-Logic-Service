package com.alibou.security.dtos;

import com.alibou.security.entities.Organisation;
import com.alibou.security.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrganisationReturnDto {

    private Integer id;

    private String name;

    private String iban;

    private String description;

    private List<User> volunteers;

    public OrganisationReturnDto(Organisation organisation) {
        this.id = organisation.getId();
        this.name = organisation.getName();
        this.iban = organisation.getIban();
        this.description = organisation.getDescription();
        this.volunteers = organisation.getVolunteers();
    }
}
