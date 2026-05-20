package com.nexstudio.expensetracker_be.dto.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionChangedEvent(
        String trxId,
        String eventType,
        String userId,
        String categoryName,
        String transactionType,
        BigDecimal amount,
        String description,
        LocalDate transactionDate,
        String paymentMethod,
        String source
) {
}
