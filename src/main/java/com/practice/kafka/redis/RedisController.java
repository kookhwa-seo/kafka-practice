package com.practice.kafka.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisController {
    final private RedisService redisService;

    @GetMapping("/save")
    public String save(@RequestParam String key, @RequestParam String value) {
        redisService.save(key, value);
        return "Saved successfully!";
    }

    @GetMapping("/get")
    public Object get(@RequestParam String key) {
        return redisService.get(key);
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String key) {
        boolean result = redisService.delete(key);
        return result ? "Deleted successfully!" : "Key not found!";
    }
}