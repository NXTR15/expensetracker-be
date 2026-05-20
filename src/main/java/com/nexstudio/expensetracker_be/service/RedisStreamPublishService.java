package com.nexstudio.expensetracker_be.service;

import com.nexstudio.expensetracker_be.dto.event.TransactionChangedEvent;

public interface RedisStreamPublishService {
    void publish(TransactionChangedEvent event);
}
