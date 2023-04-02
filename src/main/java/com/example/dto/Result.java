package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private long code;
    private String message;
    private Object data;

    public static Result success() {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), null);
    }

    public static Result success(Object object) {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), object);
    }
    public static Result error(ResultEnum resultEnum) {
        //由于失败的状态码有多种，所以这里传入枚举类
        return new Result(resultEnum.getCode(), resultEnum.getMessage(), null);
    }

    public static Result error(ResultEnum resultEnum, Object object) {
        return new Result(resultEnum.getCode(), resultEnum.getMessage(), object);
    }
}
