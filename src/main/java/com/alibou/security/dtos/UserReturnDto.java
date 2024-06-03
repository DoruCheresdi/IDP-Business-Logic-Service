package com.alibou.security.dtos;

import com.alibou.security.entities.User;
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

    private String profilePicture;

    public UserReturnDto(User user) {
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.profilePicture = user.getProfilePicture();
    }
}
