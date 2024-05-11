package com.alibou.security.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReturnDto {

    private Integer id;

    private Integer stars;

    private String title;

    private String description;
}
