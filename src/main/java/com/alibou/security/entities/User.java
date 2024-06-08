package com.alibou.security.entities;

import com.alibou.security.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String profilePicture;
    private String cvPath;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Token> tokens;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Feedback feedback;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "usersThatFavorited")
    private List<Organisation> favoritedOrganisations;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Organisation> organisationsOwned;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "domains_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "domain_id"))
    private List<ActivityDomain> domains;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role != null) {
            return role.getAuthorities();
        }
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
