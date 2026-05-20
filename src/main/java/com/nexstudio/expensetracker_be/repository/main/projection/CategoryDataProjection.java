package com.nexstudio.expensetracker_be.repository.main.projection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDataProjection {
    private String id;
    private String name;
    private String type;
}
