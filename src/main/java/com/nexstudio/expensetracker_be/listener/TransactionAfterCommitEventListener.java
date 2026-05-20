package com.nexstudio.expensetracker_be.listener;

import com.nexstudio.expensetracker_be.dto.event.TransactionChangedEvent;
import com.nexstudio.expensetracker_be.service.RedisStreamPublishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionAfterCommitEventListener {
    private final RedisStreamPublishService publishService;

    @Async("redisStreamPublisherExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAfterCommit(TransactionChangedEvent event){
        log.info("Process transaction after commit event {} and transaction for user {}",
                event.eventType(), event.userId());

        publishService.publish(event);
    }
}
