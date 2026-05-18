package com.nexstudio.expensetracker_be.service.impl;

import com.nexstudio.expensetracker_be.dto.request.TransactionRequest;
import com.nexstudio.expensetracker_be.dto.response.TransactionResponse;
import com.nexstudio.expensetracker_be.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    @Override
    public TransactionResponse saveTransaction(TransactionRequest request) {
        return null;
    }
}
