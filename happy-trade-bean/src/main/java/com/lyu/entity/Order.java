package com.lyu.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName t_order
*/
public class Order implements Serializable {

    /**
    * 订单id
    */
    @NotNull(message="[订单id]不能为空")
    @ApiModelProperty("订单id")
    private Long oid;
    /**
    * 商品id
    */
    @NotNull(message="[商品id]不能为空")
    @ApiModelProperty("商品id")
    private Long cid;
    /**
    * 卖家id
    */
    @NotNull(message="[卖家id]不能为空")
    @ApiModelProperty("卖家id")
    private Long uid;
    /**
    * 买家id
    */
    @NotNull(message="[买家id]不能为空")
    @ApiModelProperty("买家id")
    private Long uid2;
    /**
    * 下单时间
    */
    @NotNull(message="[下单时间]不能为空")
    @ApiModelProperty("下单时间")
    private Date orderTime;
    /**
    * 付款时间
    */
    @ApiModelProperty("付款时间")
    private Date payTime;
    /**
    * 发货时间
    */
    @ApiModelProperty("发货时间")
    private Date shipTime;
    /**
    * 订单完成时间
    */
    @ApiModelProperty("订单完成时间")
    private Date completeTime;
    /**
    * 收货地址id
    */
    @ApiModelProperty("收货地址id")
    private Long aid;

    /**
    * 订单id
    */
    private void setOid(Long oid){
    this.oid = oid;
    }

    /**
    * 商品id
    */
    private void setCid(Long cid){
    this.cid = cid;
    }

    /**
    * 卖家id
    */
    private void setUid(Long uid){
    this.uid = uid;
    }

    /**
    * 买家id
    */
    private void setUid2(Long uid2){
    this.uid2 = uid2;
    }

    /**
    * 下单时间
    */
    private void setOrderTime(Date orderTime){
    this.orderTime = orderTime;
    }

    /**
    * 付款时间
    */
    private void setPayTime(Date payTime){
    this.payTime = payTime;
    }

    /**
    * 发货时间
    */
    private void setShipTime(Date shipTime){
    this.shipTime = shipTime;
    }

    /**
    * 订单完成时间
    */
    private void setCompleteTime(Date completeTime){
    this.completeTime = completeTime;
    }

    /**
    * 收货地址id
    */
    private void setAid(Long aid){
    this.aid = aid;
    }


    /**
    * 订单id
    */
    private Long getOid(){
    return this.oid;
    }

    /**
    * 商品id
    */
    private Long getCid(){
    return this.cid;
    }

    /**
    * 卖家id
    */
    private Long getUid(){
    return this.uid;
    }

    /**
    * 买家id
    */
    private Long getUid2(){
    return this.uid2;
    }

    /**
    * 下单时间
    */
    private Date getOrderTime(){
    return this.orderTime;
    }

    /**
    * 付款时间
    */
    private Date getPayTime(){
    return this.payTime;
    }

    /**
    * 发货时间
    */
    private Date getShipTime(){
    return this.shipTime;
    }

    /**
    * 订单完成时间
    */
    private Date getCompleteTime(){
    return this.completeTime;
    }

    /**
    * 收货地址id
    */
    private Long getAid(){
    return this.aid;
    }

}
