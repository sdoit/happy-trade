package com.lyu.entity.dto;

import com.lyu.entity.Request;
import com.lyu.entity.UserFavoritesRequest;

/**
 * @author LEE
 * @time 2023/3/10 16:29
 */

public class UserFavoritesRequestDTO extends UserFavoritesRequest {
    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "UserFavoritesRequestDTO{" +
                "request=" + request +
                '}';
    }
}
