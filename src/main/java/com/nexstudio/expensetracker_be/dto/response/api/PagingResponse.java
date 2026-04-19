package com.nexstudio.expensetracker_be.dto.response.api;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingResponse {
    private int page;
    private int size;
    private long totalItems;
    private int totalPages;
    private Boolean hasNextPage;
    private Boolean hasPreviousPage;
}
