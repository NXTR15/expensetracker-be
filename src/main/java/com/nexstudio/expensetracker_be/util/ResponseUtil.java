package com.nexstudio.expensetracker_be.util;

import com.nexstudio.expensetracker_be.dto.response.api.ApiResponse;
import com.nexstudio.expensetracker_be.dto.response.api.ErrorResponse;
import com.nexstudio.expensetracker_be.dto.response.api.PagingResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseUtil {
    private ResponseUtil() {
        throw new IllegalStateException("Utility Class");
    }

    public static <T> ResponseEntity<ApiResponse<T>> buildSingleResponse(HttpStatus status, String message, T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .status(status.value())
                .httpMesssage(status.getReasonPhrase())
                .message(message)
                .data(data)
                .build();

        return ResponseEntity.status(status).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<List<T>>> buildPageResponse(
            HttpStatus status,
            String message,
            Page<T> data) {
        PagingResponse pagingBuild = PagingResponse.builder()
                .page(data.getNumber() + 1)
                .size(data.getSize())
                .totalItems(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .hasNextPage(data.hasNext())
                .hasPreviousPage(data.hasPrevious())
                .build();

        ApiResponse<List<T>> response = ApiResponse.<List<T>>builder()
                .status(status.value())
                .httpMesssage(status.getReasonPhrase())
                .message(message)
                .data(data.getContent())
                .paging(pagingBuild)
                .build();

        return ResponseEntity.status(status).body(response);
    }

    public static ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            String message,
            List<String> errors) {

        ErrorResponse response = ErrorResponse.builder()
                .status(status.value())
                .httpMesssage(status.getReasonPhrase())
                .message(message)
                .errors(errors).build();

        return ResponseEntity.status(status).body(response);
    }
}
