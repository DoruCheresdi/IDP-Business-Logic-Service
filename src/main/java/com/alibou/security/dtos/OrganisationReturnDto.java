package com.alibou.security.dtos;

import com.alibou.security.entities.Organisation;
import com.alibou.security.entities.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationReturnDto {

    private Integer id;

    private String name;

    private String iban;

    private String description;

    private List<UserReturnDto> volunteers;

    public OrganisationReturnDto(Organisation organisation) {
        this.id = organisation.getId();
        this.name = organisation.getName();
        this.iban = organisation.getIban();
        this.description = organisation.getDescription();
        if (organisation.getVolunteers() != null) {
            this.volunteers = organisation.getVolunteers().stream().map(UserReturnDto::new).collect(Collectors.toList());
        }
    }
}
