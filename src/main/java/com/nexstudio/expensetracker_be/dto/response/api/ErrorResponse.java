package com.nexstudio.expensetracker_be.dto.response.api;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private Integer status;
    private String httpMesssage;
    private String message;

    private List<String> errors;
}

