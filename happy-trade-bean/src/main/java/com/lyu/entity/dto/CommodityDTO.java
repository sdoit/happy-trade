package com.lyu.entity.dto;

import com.lyu.entity.Commodity;
import com.lyu.entity.UserResource;
import lombok.Data;

import java.util.List;

/**
 * @author LEE
 * @time 2023/1/29 15:35
 */
@Data
public class CommodityDTO extends Commodity {

    /**
     * 类型
     */
    private String typeName;


    /**
     * 封面
     */
    private UserResource cover;



    /**
     * 描述资源（描述图片、描述视频等）
     */
    private List<UserResource> resources;

}
