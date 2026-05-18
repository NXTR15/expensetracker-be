package com.nexstudio.expensetracker_be.repository.main.impl;

import com.nexstudio.expensetracker_be.constants.Constants;
import com.nexstudio.expensetracker_be.entity.UserEntity;
import com.nexstudio.expensetracker_be.enums.UserRole;
import com.nexstudio.expensetracker_be.repository.main.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void saveUser(UserEntity user) {
        LocalDateTime now = LocalDateTime.now();

        String sql = "INSERT INTO " + Constants.USER_TABLE +
                "(id, username, name, password, email, role, status, inbound_address, created_at, updated_at)"
                + Constants.VALUES_SQL +
                "(:id, :username, :name, :password, :email, :role, :status, :inbound_address, :created_at, :updated_at)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", UUID.randomUUID().toString().replace("-", ""))
                .addValue("username", user.getUsername())
                .addValue("name", user.getName())
                .addValue("password", user.getPassword())
                .addValue("email", user.getEmail())
                .addValue("role", user.getRole() == UserRole.USER ? "USER" : "ADMIN")
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

    @Override
    public UserEntity getUser(String username){
        String sql = "SELECT id, username, name, password, email, role, status, inbound_address " +
                "FROM " + Constants.USER_TABLE +
                "WHERE username = :username";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", username);

        try {
            log.info("==== Sql get user by username: {} ====", sql);

            return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> UserEntity.builder()
                    .id(rs.getString("id"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .email(rs.getString("email"))
                    .role(Objects.equals(rs.getString("role"), UserRole.ADMIN.getDisplayName()) ? UserRole.ADMIN : UserRole.USER)
                    .status(rs.getString("status"))
                    .inboundAddress(rs.getString("inbound_address"))
                    .build());
        } catch (EmptyResultDataAccessException e) {
            log.info("=== User with username: {} not found ===", username);
            return null;
        } catch (DataAccessException e) {
            log.error("=== Failed to get user with username: {} ===", username);
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public UserEntity getUserByUsernameOrEmail(String usernameOrEmail) {
        String sql = "SELECT id, username, name, password, email, role, status, inbound_address " +
                "FROM " + Constants.USER_TABLE +
                "WHERE username = :usernameOrEmail OR email = :usernameOrEmail";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("usernameOrEmail", usernameOrEmail);

        try {
            log.info("==== Sql get user by username or email user: {} ====", sql);

            return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> UserEntity.builder()
                    .id(rs.getString("id"))
                    .username(rs.getString("username"))
                    .name(rs.getString("name"))
                    .password(rs.getString("password"))
                    .email(rs.getString("email"))
                    .role(Objects.equals(rs.getString("role"), UserRole.ADMIN.getDisplayName()) ? UserRole.ADMIN : UserRole.USER)
                    .status(rs.getString("status"))
                    .inboundAddress(rs.getString("inbound_address"))
                    .build());
        } catch (EmptyResultDataAccessException e) {
            log.info("=== User with username/email: {} not found ===", usernameOrEmail);
            return null;
        } catch (DataAccessException e) {
            log.error("=== Failed to get user with username/email: {} ===", usernameOrEmail);
            log.error(e.getMessage());
            throw e;
        }
    }
}
