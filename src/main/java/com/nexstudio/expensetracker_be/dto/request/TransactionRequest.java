package com.nexstudio.expensetracker_be.dto.request;

import com.nexstudio.expensetracker_be.enums.PaymentMethod;
import com.nexstudio.expensetracker_be.enums.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {
    private String username;
    private String categoryCode;
    private BigDecimal amount;
    private String description;
    private String trxDate;
    private PaymentMethod paymentMethod;
    private Source source;
}
