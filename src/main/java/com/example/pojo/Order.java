package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zhoubin
 * @since 2022-07-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    @TableField("goodsId")
    private Long goodsId;

    /**
     * 买方id
     */
    @TableField("buyerId")
    private Long buyerId;

    /**
     * 卖方id
     */
    @TableField("sellerId")
    private Long sellerId;

    /**
     * 订单创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("orderTime")
    private Date orderTime;

    /**
     * 总金额数
     */
    @TableField("totalAmount")
    private Double totalAmount;


    /**
     * 订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成
     */
    private Integer status;

    @TableField(exist = false)
    private String goodName;

    @TableField(exist = false)
    private String url;

    @TableField(exist = false)
    private String statusText;

    @TableField(exist = false)
    private String button;

    @TableField(exist = false)
    private String buyerName;

    @TableField(exist = false)
    private String address;

    @TableField(exist = false)
    private Boolean isButton;
}
