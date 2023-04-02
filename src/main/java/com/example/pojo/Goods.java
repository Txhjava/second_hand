package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 商品图片地址
     */
    private String url;

    /**
     * 商品介绍
     */
    private String description;

    //商品状态
    private Long status;

    //卖方id
    @TableField("seller_id")
    private Long sellerId;

    //商品类型
    private String type;

    //商品名字
    @TableField("good_name")
    private String goodName;

    //用户名
    @TableField(exist = false)
    private String name;
    //用户头像
    @TableField(exist = false)
    private String icon;


}
