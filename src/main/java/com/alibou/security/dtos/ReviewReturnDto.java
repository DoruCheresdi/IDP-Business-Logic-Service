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

    private Integer organisationId;

    private String reviewerEmail;

    private String reviewerName;

    public ReviewReturnDto(Review review) {
        this.id = review.getId();
        this.stars = review.getStars();
        this.title = review.getTitle();
        this.description = review.getDescription();
        this.reviewerEmail = review.getReviewer().getEmail();
        this.reviewerName = review.getReviewer().getFirstname() + " " + review.getReviewer().getLastname();
        if (review.getOrganisationReviewed() != null) {
            this.organisationId = review.getOrganisationReviewed().getId();
        } else {
            this.organisationId = null;
        }
    }
}
