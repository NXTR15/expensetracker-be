package com.nexstudio.expensetracker_be.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexstudio.expensetracker_be.dto.event.TransactionChangedEvent;
import com.nexstudio.expensetracker_be.properties.RedisStreamProperties;
import com.nexstudio.expensetracker_be.service.RedisStreamPublishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
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
            log.info("Received Json Payload : {}", event);

            MapRecord<String, String, String> record = getEntries(event);

            log.info("Sending payload to redis stream.....");

            RecordId recordId = redisTemplate.opsForStream().add(record);

            redisTemplate.opsForStream().trim(
                    redisStreamProperties.getKey(),
                    100,
                    true
            );

            log.info("Successfully published Redis Stream event. stream={}, recordId={}, transactionId={}",
                    redisStreamProperties.getKey(), recordId, event.trxId());

        } catch (Exception e){
            throw new RuntimeException("Failed to publish Redis Stream", e);
        }
    }

    private @NonNull MapRecord<String, String, String> getEntries(TransactionChangedEvent event) {
        Map<String, String> redisFields = Map.of(
                "trxId", event.trxId(),
                "eventType", event.eventType(),
                "userId", event.userId(),
                "categoryName", event.categoryName(),
                "transactionType", event.transactionType(),
                "amount", event.amount().toPlainString(),
                "description", event.description(),
                "transactionDate", event.transactionDate().toString(),
                "paymentMethod", event.paymentMethod(),
                "source", event.source()
        );

        MapRecord<String, String, String> record = MapRecord.create(
                redisStreamProperties.getKey(),
                redisFields
        );
        return record;
    }
}
