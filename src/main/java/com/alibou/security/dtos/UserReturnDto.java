package com.alibou.security.dtos;

import com.alibou.security.entities.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReturnDto {

    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private String profilePicture;

    private String cvPath;

    private List<DomainDto> domains;

    public UserReturnDto(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.profilePicture = user.getProfilePicture();
        this.cvPath = user.getCvPath();
        if (user.getDomains() != null) {
            this.domains = user.getDomains().stream().map(DomainDto::new).toList();
        }
    }
}
