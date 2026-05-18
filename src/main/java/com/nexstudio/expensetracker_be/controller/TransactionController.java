package com.nexstudio.expensetracker_be.controller;

import com.nexstudio.expensetracker_be.dto.request.TransactionRequest;
import com.nexstudio.expensetracker_be.dto.response.TransactionResponse;
import com.nexstudio.expensetracker_be.dto.response.api.ApiResponse;
import com.nexstudio.expensetracker_be.service.TransactionService;
import com.nexstudio.expensetracker_be.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<ApiResponse<TransactionResponse>> saveTransaction(@RequestBody TransactionRequest request){
        log.info("=== STARTING /transaction SAVE DATA ENDPOINT ===");

        TransactionResponse response = transactionService.saveTransaction(request);
        return ResponseUtil.buildSingleResponse(HttpStatus.OK, "Save transaction success", response);
    }
}
