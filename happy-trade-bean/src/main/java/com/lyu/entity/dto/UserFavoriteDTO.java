package com.lyu.entity.dto;

import com.lyu.entity.Commodity;
import com.lyu.entity.UserFavorite;
import com.lyu.entity.UserResource;
import lombok.Data;

/**
 * @author LEE
 * @time 2023/2/11 15:05
 */
@Data
public class UserFavoriteDTO extends UserFavorite {
    private Commodity commodity;
    private UserResource cover;

}
