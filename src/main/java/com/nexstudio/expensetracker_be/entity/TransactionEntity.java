package com.nexstudio.expensetracker_be.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEntity {
    private String id;
    private String userId;
    private String categoryId;
    private BigDecimal amount;
    private String description;
    private LocalDateTime transactionDate;
    private String paymentMethod;
    private String source;
    private String createdAt;
    private String updatedAt;
}
