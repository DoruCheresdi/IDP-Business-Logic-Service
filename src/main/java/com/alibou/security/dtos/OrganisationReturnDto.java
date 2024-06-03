package com.alibou.security.dtos;

import com.alibou.security.entities.Organisation;
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

    private Boolean isApproved;

    private Boolean isFeatured;

    private List<UserReturnDto> usersThatFavorited;

    private Integer ownerId;

    private String ownerEmail;

    private String orgLink;

    public OrganisationReturnDto(Organisation organisation) {
        this.id = organisation.getId();
        this.name = organisation.getName();
        this.iban = organisation.getIban();
        this.description = organisation.getDescription();
        this.isApproved = organisation.getIsApproved();
        this.isFeatured = organisation.getIsFeatured();
        this.ownerId = organisation.getOwner().getId();
        this.ownerEmail = organisation.getOwner().getEmail();
        this.orgLink = organisation.getOrgLink();
        if (organisation.getUsersThatFavorited() != null) {
            this.usersThatFavorited = organisation.getUsersThatFavorited().stream().map(UserReturnDto::new).collect(Collectors.toList());
        }
    }
}
