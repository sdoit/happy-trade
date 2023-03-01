package com.lyu.entity.dto;

import com.lyu.entity.User;
import lombok.Data;

/**
 * @author LEE
 * @time 2023/2/25 19:30
 */
@Data
public class UserWithRatingDTO extends User {
    private Integer goodRatingCountSeller;
    private Integer ratingCountSeller;
    private Integer goodRatingCountBuyer;
    private Integer ratingCountBuyer;

}
