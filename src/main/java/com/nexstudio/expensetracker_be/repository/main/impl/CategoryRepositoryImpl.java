package com.nexstudio.expensetracker_be.repository.main.impl;

import com.nexstudio.expensetracker_be.constants.Constants;
import com.nexstudio.expensetracker_be.repository.main.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String getCategoryIdByName(String categoryName) {
        String sql = "SELECT id FROM " + Constants.CATEGORY_TABLE +
                "WHERE LOWER(code) = LOWER(:name) AND status = :status";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", categoryName)
                .addValue("status", Constants.ACTIVE);

        try {
            log.info("==== Sql get category by name: {} ====", sql);
            return namedParameterJdbcTemplate.queryForObject(sql, params, String.class);
        } catch (EmptyResultDataAccessException e) {
            log.info("=== Category with name: {} not found ===", categoryName);
            return null;
        } catch (DataAccessException e) {
            log.error("=== Failed to get category with name: {} ===", categoryName);
            log.error(e.getMessage());
            throw e;
        }
    }
}
