package com.example;

import org.junit.jupiter.api.Test;
import org.mockito.internal.util.io.IOUtil;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class SecondHandApplicationTests {

    @Test
    void contextLoads() {
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(currentTimeMillis);
    }

    @Test
    void ecoding(){
        String content = "%E5%88%B0%E5%BA%95%E6%98%AF%E4%B8%BA%E4%BB%80%E4%B9%88%E5%91%A2";
        content = new String(content.getBytes(), StandardCharsets.UTF_8);
        System.out.println(content);
    }

}
