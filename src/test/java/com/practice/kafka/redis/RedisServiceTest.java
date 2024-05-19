package com.practice.kafka.redis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RedisServiceTest {
    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    String key = "testKey";
    String value = "testValue";

    @AfterEach
    public void teardown() {
        redisTemplate.delete(key);
    }

    @Test
    public void testSaveWithExpiration() throws InterruptedException {
        redisService.saveWithExpiration(key, value, 3, TimeUnit.SECONDS);
        Object retrievedValue = redisService.get(key);

        assertEquals(value, retrievedValue);

        Thread.sleep(1000*4);
        assertEquals(null, redisService.get(key));
    }

    @Test
    void testSave() {
        redisService.save(key, value);
        Object retrievedValue = redisService.get(key);
        assertEquals(value, retrievedValue);
    }

    @Test
    void testGet() {
        redisService.save(key, value);
        Object retrievedValue = redisService.get(key);
        assertNotNull(retrievedValue);
    }

    @Test
    void testDelete() {
        redisService.save(key, value);
        redisService.delete(key);
        Object retrievedValue = redisService.get(key);
        assertEquals(null, retrievedValue);
    }
}
