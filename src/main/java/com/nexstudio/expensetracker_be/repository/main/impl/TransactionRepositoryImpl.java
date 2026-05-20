package com.nexstudio.expensetracker_be.repository.main.impl;

import com.nexstudio.expensetracker_be.constants.Constants;
import com.nexstudio.expensetracker_be.entity.TransactionEntity;
import com.nexstudio.expensetracker_be.repository.main.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@Slf4j
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String saveTransaction(TransactionEntity entity) {
        LocalDateTime now = LocalDateTime.now();

        String sql = "INSERT INTO " + Constants.TRANSACTION_TABLE +
                "(id, user_id, category_code, amount, description, transaction_date, payment_method, source, transaction_type, created_at, updated_at)"
                + Constants.VALUES_SQL +
                "(:id, :user_id, :category_code, :amount, :description, :transaction_date, :payment_method, :source, :transaction_type, :created_at, :updated_at)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("user_id", entity.getUserId())
                .addValue("category_code", entity.getCategoryCode())
                .addValue("amount", entity.getAmount())
                .addValue("description", entity.getDescription())
                .addValue("transaction_date", entity.getTransactionDate())
                .addValue("transaction_type", entity.getTransactionType())
                .addValue("payment_method", entity.getPaymentMethod())
                .addValue("source", entity.getSource())
                .addValue("created_at", now)
                .addValue("updated_at", null);

        try {
            namedParameterJdbcTemplate.update(sql, params);
            log.info("==== Sql save transaction: {} ====", sql);

            return entity.getId();
        } catch (DataAccessException e) {
            log.error("=== Failed to save transaction with id: {} ===", entity.getId());
            log.error(e.getMessage());
            throw e;
        }
    }
}
