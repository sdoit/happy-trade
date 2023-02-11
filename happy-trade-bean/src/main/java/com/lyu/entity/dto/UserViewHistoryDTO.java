package com.lyu.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lyu.entity.Commodity;
import com.lyu.entity.UserResource;
import com.lyu.entity.UserViewHistory;
import lombok.Data;

/**
 * @author LEE
 * @time 2023/1/31 19:31
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserViewHistoryDTO extends UserViewHistory {
    private Commodity commodity;

    private UserResource cover;
}
