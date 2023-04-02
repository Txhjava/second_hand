package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Userinform implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用于与user表对应
     */
    @TableField("userId")
    private Long userId;

    /**
     * 地址
     */
    private String adress;

    /**
     * 邮箱
     */
    private String email;

    @TableField("NAME")
    private String name;

    /**
     * 性别
     */
    private String sex;

    private Integer age;

    /**
     * 生日
     */
    private Date birth;

    /**
     * 头像路径
     */
    private String url;

    @TableField(exist = false)
    private String phone;
}
