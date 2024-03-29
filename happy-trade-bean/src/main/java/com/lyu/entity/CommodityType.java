package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Arrays;
import java.util.List;

/**
 * @author LEE
 * @time 2023/2/15 13:55
 */
@TableName(value = "t_commodity_type")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommodityType {
    @TableId
    private Integer tid;
    private String typeName;
    private Integer pTid;
    private String img;
    @TableField(exist = false)
    private Integer[] typePath;
    @TableField(exist = false)
    @JsonIgnore
    private Integer tidRoot;
    @TableField(exist = false)
    @JsonIgnore
    private Integer tidMiddle;
    @TableField(exist = false)
    List<CommodityType> commodityTypes;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getpTid() {
        return pTid;
    }

    public void setpTid(Integer pTid) {
        this.pTid = pTid;
    }

    public Integer[] getTypePath() {
        return typePath;
    }

    public void setTypePath(Integer[] typePath) {
        this.typePath = typePath;
    }

    public Integer getTidRoot() {
        return tidRoot;
    }

    public void setTidRoot(Integer tidRoot) {
        this.tidRoot = tidRoot;
    }

    public Integer getTidMiddle() {
        return tidMiddle;
    }

    public void setTidMiddle(Integer tidMiddle) {
        this.tidMiddle = tidMiddle;
    }

    public List<CommodityType> getCommodityTypes() {
        return commodityTypes;
    }

    public void setCommodityTypes(List<CommodityType> commodityTypes) {
        this.commodityTypes = commodityTypes;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "CommodityType{" +
                "tid=" + tid +
                ", typeName='" + typeName + '\'' +
                ", pTid=" + pTid +
                ", typePath=" + Arrays.toString(typePath) +
                ", tidRoot=" + tidRoot +
                ", tidMiddle=" + tidMiddle +
                ", commodityTypes=" + commodityTypes +
                '}';
    }
}
