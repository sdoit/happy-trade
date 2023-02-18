package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author LEE
 * @time 2023/2/15 13:55
 */
@TableName(value = "t_commodity_type")
@Data
public class CommodityType {
    private Integer tid;
    private String  typeName;
    private Integer pTid;

}
