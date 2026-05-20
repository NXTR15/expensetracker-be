package com.nexstudio.expensetracker_be.service.impl;

import com.nexstudio.expensetracker_be.dto.request.TransactionRequest;
import com.nexstudio.expensetracker_be.dto.response.TransactionResponse;
import com.nexstudio.expensetracker_be.entity.TransactionEntity;
import com.nexstudio.expensetracker_be.entity.UserEntity;
import com.nexstudio.expensetracker_be.repository.main.CategoryRepository;
import com.nexstudio.expensetracker_be.repository.main.TransactionRepository;
import com.nexstudio.expensetracker_be.repository.main.UserRepository;
import com.nexstudio.expensetracker_be.service.TransactionService;
import com.nexstudio.expensetracker_be.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final ValidationUtils validationUtils;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public TransactionResponse saveTransaction(TransactionRequest request) {
        validationUtils.validateTransactionRequest(request);
        LocalDateTime transactionDate = parseTransactionDate(request.getTrxDate());

        UserEntity user = userRepository.getUser(request.getUsername().toLowerCase());
        if (user == null) {
            log.error("User not found!");
            throw new IllegalArgumentException("usr.error.001");
        }

        String categoryId = categoryRepository.getCategoryIdByCode(request.getCategoryCode());
        if (categoryId == null) {
            log.error("Category not found!");
            throw new IllegalArgumentException("cat.error.001");
        }

        TransactionEntity transaction = TransactionEntity.builder()
                .userId(user.getId())
                .categoryId(categoryId)
                .amount(request.getAmount())
                .description(request.getDescription())
                .transactionDate(transactionDate)
                .paymentMethod(request.getPaymentMethod().name())
                .source(request.getSource().name())
                .build();

        String transactionId = transactionRepository.saveTransaction(transaction);
        log.info("Transaction successfully created with id {}", transactionId);

        return TransactionResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .category(request.getCategoryCode())
                .amount(request.getAmount())
                .description(request.getDescription())
                .transactionDate(transactionDate)
                .paymentMethod(request.getPaymentMethod().name())
                .source(request.getSource().name())
                .build();
    }

    private LocalDateTime parseTransactionDate(String trxDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate parsedDate = LocalDate.parse(trxDate, formatter);
            return parsedDate.atTime(23, 59, 29);
        } catch (DateTimeParseException exception) {
            log.error("Failed to parse date : {}", trxDate);
            throw new IllegalArgumentException("Failed parse date!", exception);
        }
    }
}
