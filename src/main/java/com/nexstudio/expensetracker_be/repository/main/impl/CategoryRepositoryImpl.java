package com.nexstudio.expensetracker_be.repository.main.impl;

import com.nexstudio.expensetracker_be.constants.Constants;
import com.nexstudio.expensetracker_be.repository.main.CategoryRepository;
import com.nexstudio.expensetracker_be.repository.main.projection.CategoryDataProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public CategoryDataProjection getCategoryDataByCode(String categoryCode) {
        String sql = "SELECT id, name, type FROM " + Constants.CATEGORY_TABLE +
                " WHERE LOWER(code) = LOWER(:code) AND status = :status";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("code", categoryCode)
                .addValue("status", Constants.ACTIVE);

        try {
            log.info("==== Sql get category by code: {} ====", sql);
            return namedParameterJdbcTemplate.queryForObject(
                    sql,
                    params,
                    BeanPropertyRowMapper.newInstance(CategoryDataProjection.class)
            );
        } catch (EmptyResultDataAccessException e) {
            log.info("=== Category with code: {} not found ===", categoryCode);
            return null;
        } catch (DataAccessException e) {
            log.error("=== Failed to get category with code: {} ===", categoryCode);
            log.error(e.getMessage());
            throw e;
        }
    }
}
