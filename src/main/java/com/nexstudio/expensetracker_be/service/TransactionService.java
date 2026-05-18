package com.nexstudio.expensetracker_be.service;

import com.nexstudio.expensetracker_be.dto.request.TransactionRequest;
import com.nexstudio.expensetracker_be.dto.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse saveTransaction(TransactionRequest request);
}
