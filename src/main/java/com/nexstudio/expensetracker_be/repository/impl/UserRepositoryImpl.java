package com.nexstudio.expensetracker_be.repository.impl;

import com.nexstudio.expensetracker_be.constants.Constants;
import com.nexstudio.expensetracker_be.entity.UserEntity;
import com.nexstudio.expensetracker_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void saveUser(UserEntity user) {
        LocalDateTime now = LocalDateTime.now();

        String sql = "INSERT INTO " + Constants.DEFAULT_SCHEMA + Constants.USER_TABLE +
                "(id, username, password, email, role, status, inbound_address, created_at, updated_at)"
                + Constants.VALUES_SQL +
                "(:id, :username, :password, :email, :role, :status, :inbound_address, :created_at, :updated_at)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("username", user.getUsername())
                .addValue("password", user.getPassword())
                .addValue("email", user.getEmail())
                .addValue("role", user.getRole())
                .addValue("status", user.getStatus())
                .addValue("inbound_address", user.getInboundAddress())
                .addValue("created_at", Timestamp.valueOf(now))
                .addValue("updated_at", null);

        try {
            namedParameterJdbcTemplate.update(sql, params);
            log.info("==== Sql save user: {} ====", sql);
        } catch (DataAccessException e) {
            log.error("=== Failed to save user with id: {} ===", user.getId());
            log.error(e.getMessage());
            throw e;
        }
    }
}
