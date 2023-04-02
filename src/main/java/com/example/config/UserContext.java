package com.example.config;

import com.example.dto.UserDTO;
import com.example.pojo.User;

public class UserContext {
    private static ThreadLocal<UserDTO> userHolder = new ThreadLocal<>();

    public static void setUser(UserDTO user) {
        userHolder.set(user);
    }

    public static UserDTO getUser() {
        return userHolder.get();
    }
}
