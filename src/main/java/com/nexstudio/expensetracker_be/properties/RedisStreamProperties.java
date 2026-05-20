package com.nexstudio.expensetracker_be.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "redis.stream")
public class RedisStreamProperties {
    private String key;
    private String group;
    private String consumer;
}
