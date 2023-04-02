package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

//公共返回对象枚举
@Getter
@ToString
@AllArgsConstructor
public enum ResultEnum {
    SUCCESS(200, "成功"),
    ERROR(500, "服务器错误"),
    //登录模块
    LOGIN_ERROR(5011, "用户名或密码错误"),
    MOBILE_ERROR(5012, "手机号码格式不正确"),
    USER_NOT_EXIT(5013, "用户不存在，请注册"),
    REPEAT_REGISTER_ERROR(5014, "您已注册，请登录"),
    NO_LOGIN(5015, "您还未登录"),
    EMAIL_ERROR(5016, "邮箱格式不正确"),
    //二手商品模块
    ADD_GOOD_ERROR(5021,"发布失败"),
    GOOD_NOT_EXIT(5022, "商品不存在"),
    //个人信息模块
    NOT_PUBLISH_GOODS(5031, "您还没有发布商品"),
    //购物车
    NOT_LIKE_GOODS(5041,"您还没有喜欢的商品，快去选购吧"),
    DELETE_ERROR(5042, "删除错误，请重试"),
    BUY_ERROR(5043, "购买失败，请重试"),
    NOT_SELL_GOOD(5044, "您还未卖出商品"),
    NOT_BUY_GOOD(5045,"您还未买商品");

    private final Integer code;
    private final String message;
}
