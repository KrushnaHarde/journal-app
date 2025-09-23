package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    void test(){
        redisTemplate.opsForValue().set("email", "abc@test.com");
        Object email = redisTemplate.opsForValue().get("email");
        Object name = redisTemplate.opsForValue().get("name");

        Assertions.assertNotNull(email);
        Assertions.assertNotNull(name);
    }
}
