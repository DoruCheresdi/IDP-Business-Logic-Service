package com.alibou.security.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewDto {

    @NotNull(message = "Stars cannot be null")
    @Min(value = 1, message = "Stars should be between 1 and 5")
    @Max(value = 5, message = "Stars should be between 1 and 5")
    private Integer stars;

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 255, message = "Title should be between 1 and 255 characters")
    private String title;

    @NotNull(message = "Description cannot be null")
    @Size(min = 1, max = 1000, message = "Description should be between 1 and 1000 characters")
    private String description;

    @NotNull(message = "Organisation ID cannot be null")
    private Integer organisationId;
}
