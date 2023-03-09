package com.lyu.entity.dto;

import com.lyu.entity.CommodityType;
import com.lyu.entity.Request;
import com.lyu.entity.Tag;

import java.util.List;

/**
 * @author LEE
 * @time 2023/3/8 16:34
 */
public class RequestDTO extends Request {
    /**
     * 该商品的出价数量
     */
    private Integer bidCount;
    private CommodityType type;

    /**
     * 卖家信息
     */
    private UserWithRatingDTO user;
    /**
     * 商品标签
     */
    private List<Tag> tags;

    /**
     * 已收藏？
     */
    private Boolean favorite;

    public Integer getBidCount() {
        return bidCount;
    }

    public void setBidCount(Integer bidCount) {
        this.bidCount = bidCount;
    }

    public CommodityType getType() {
        return type;
    }

    public void setType(CommodityType type) {
        this.type = type;
    }

    public UserWithRatingDTO getUser() {
        return user;
    }

    public void setUser(UserWithRatingDTO user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "bidCount=" + bidCount +
                ", type=" + type +
                ", user=" + user +
                ", tags=" + tags +
                ", favorite=" + favorite +
                '}';
    }
}
