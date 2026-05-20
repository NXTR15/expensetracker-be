package com.nexstudio.expensetracker_be.repository.main;

import com.nexstudio.expensetracker_be.repository.main.projection.CategoryDataProjection;

public interface CategoryRepository {
    CategoryDataProjection getCategoryDataByCode(String categoryCode);
}
