package com.nexstudio.expensetracker_be.dto.response;

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
public class TransactionResponse {
    private String userName;
    private String category;
    private BigDecimal amount;
    private String description;
    private LocalDateTime transactionDate;
    private String paymentMethod;
    private String source;
}
