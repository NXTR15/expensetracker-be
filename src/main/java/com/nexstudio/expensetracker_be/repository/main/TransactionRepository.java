package com.nexstudio.expensetracker_be.repository.main;

import com.nexstudio.expensetracker_be.entity.TransactionEntity;

public interface TransactionRepository {
    void saveTransaction(TransactionEntity entity);
}
