package com.alibou.security.dtos;

import com.alibou.security.entities.Review;
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

    public ReviewReturnDto(Review review) {
        this.id = review.getId();
        this.stars = review.getStars();
        this.title = review.getTitle();
        this.description = review.getDescription();
    }
}
