package com.lyu.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lyu.entity.Commodity;
import com.lyu.entity.User;
import com.lyu.entity.UserResource;
import lombok.Data;

import java.util.List;

/**
 * @author LEE
 * @time 2023/1/29 15:35
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class CommodityDTO extends Commodity {




    /**
     * 类型
     */
    private String typeName;


    /**
     * 卖家信息
     */
    private User user;

    /**
     * 封面
     */
    private UserResource cover;



    /**
     * 描述资源（描述图片、描述视频等）
     */
    private List<UserResource> resources;

}
