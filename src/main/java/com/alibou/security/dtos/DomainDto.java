package com.alibou.security.dtos;

import com.alibou.security.entities.ActivityDomain;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainDto {

    private Integer id;

    @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
    private String name;

    public DomainDto(ActivityDomain domain) {
        this.id = domain.getId();
        this.name = domain.getName();
    }
}
