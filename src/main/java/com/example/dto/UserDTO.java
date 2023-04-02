package com.example.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private Long id;

    private String name;

    private String url;
}
