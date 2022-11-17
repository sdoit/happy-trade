package com.lyu.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName t_commodity
*/
public class Commodity implements Serializable {

    /**
    * 商品ID
    */
    @NotNull(message="[商品ID]不能为空")
    @ApiModelProperty("商品ID")
    private Long cid;
    /**
    * 商品名
    */
    @NotBlank(message="[商品名]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("商品名")
    @Length(max= 50,message="编码长度不能超过50")
    private String name;
    /**
    * 商品品质 0-10
    */
    @NotNull(message="[商品品质 0-10]不能为空")
    @ApiModelProperty("商品品质 0-10")
    private Double quality;
    /**
    * 商品类型
    */
    @NotBlank(message="[商品类型]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("商品类型")
    @Length(max= 255,message="编码长度不能超过255")
    private String type;
    /**
    * 价格
    */
    @NotNull(message="[价格]不能为空")
    @ApiModelProperty("价格")
    private Double prince;
    /**
    * 商品说明/商品描述
    */
    @Size(max= -1,message="编码长度不能超过-1")
    @ApiModelProperty("商品说明/商品描述")
    @Length(max= -1,message="编码长度不能超过-1")
    private String description;
    /**
    * 商品图片
    */
    @ApiModelProperty("商品图片")
    private Object img;

    /**
    * 商品ID
    */
    private void setCid(Long cid){
    this.cid = cid;
    }

    /**
    * 商品名
    */
    private void setName(String name){
    this.name = name;
    }

    /**
    * 商品品质 0-10
    */
    private void setQuality(Double quality){
    this.quality = quality;
    }

    /**
    * 商品类型
    */
    private void setType(String type){
    this.type = type;
    }

    /**
    * 价格
    */
    private void setPrince(Double prince){
    this.prince = prince;
    }

    /**
    * 商品说明/商品描述
    */
    private void setDescription(String description){
    this.description = description;
    }

    /**
    * 商品图片
    */
    private void setImg(Object img){
    this.img = img;
    }


    /**
    * 商品ID
    */
    private Long getCid(){
    return this.cid;
    }

    /**
    * 商品名
    */
    private String getName(){
    return this.name;
    }

    /**
    * 商品品质 0-10
    */
    private Double getQuality(){
    return this.quality;
    }

    /**
    * 商品类型
    */
    private String getType(){
    return this.type;
    }

    /**
    * 价格
    */
    private Double getPrince(){
    return this.prince;
    }

    /**
    * 商品说明/商品描述
    */
    private String getDescription(){
    return this.description;
    }

    /**
    * 商品图片
    */
    private Object getImg(){
    return this.img;
    }

}
