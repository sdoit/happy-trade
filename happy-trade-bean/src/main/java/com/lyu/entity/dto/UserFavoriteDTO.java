package com.lyu.entity.dto;

import com.lyu.entity.Commodity;
import com.lyu.entity.Request;
import com.lyu.entity.UserFavorite;
import com.lyu.entity.UserResource;

/**
 * @author LEE
 * @time 2023/2/11 15:05
 */
public class UserFavoriteDTO extends UserFavorite {
    private Commodity commodity;
    private Request request;
    private UserResource cover;

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public UserResource getCover() {
        return cover;
    }

    public void setCover(UserResource cover) {
        this.cover = cover;
    }
}
