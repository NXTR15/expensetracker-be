package com.nexstudio.expensetracker_be.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexstudio.expensetracker_be.dto.event.TransactionChangedEvent;
import com.nexstudio.expensetracker_be.properties.RedisStreamProperties;
import com.nexstudio.expensetracker_be.service.RedisStreamPublishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisStreamPublishServiceImpl implements RedisStreamPublishService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final RedisStreamProperties redisStreamProperties;

    @Override
    public void publish(TransactionChangedEvent event) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(event);

            log.info("Created Json Payload : {}", jsonPayload);

            Map<String, String> redisFields = Map.of(
                    "payload", jsonPayload
            );

            MapRecord<String, String, String> record = MapRecord.create(
                    redisStreamProperties.getKey(),
                    redisFields
            );

            log.info("Sending payload to redis stream.....");

            RecordId recordId = redisTemplate.opsForStream().add(record);

            log.info("Successfully published Redis Stream event. stream={}, recordId={}, transactionId={}",
                    redisStreamProperties.getKey(), recordId, event.trxId());

        } catch (JsonProcessingException e){
            throw new IllegalStateException("Failed to serialize Redis Stream payload", e);
        } catch (Exception e){
            throw new RuntimeException("Failed to publish Redis Stream", e);
        }
    }
}
