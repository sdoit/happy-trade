package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author LEE
 * @time 2023/2/15 13:55
 */
@TableName(value = "t_commodity_type")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommodityType {
    private Integer tid;
    private String typeName;
    private Integer pTid;

    @TableField(exist = false)
    private Integer[] typePath;
    @TableField(exist = false)
    @JsonIgnore
    private Integer tidRoot;
    @TableField(exist = false)
    @JsonIgnore
    private Integer tidMiddle;

}
