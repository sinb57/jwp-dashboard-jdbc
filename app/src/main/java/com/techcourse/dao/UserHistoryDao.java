package com.techcourse.dao;

import com.techcourse.domain.UserHistory;

import nextstep.jdbc.JdbcTemplate;

public class UserHistoryDao {

    private final JdbcTemplate jdbcTemplate;

    public UserHistoryDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void log(final UserHistory userHistory) {
        final var userId = userHistory.getUserId();
        final var account = userHistory.getAccount();
        final var password = userHistory.getPassword();
        final var email = userHistory.getEmail();
        final var createdAt = userHistory.getCreatedAt();
        final var createBy = userHistory.getCreateBy();

        final var sql = "insert into user_history (user_id, account, password, email, created_at, created_by)"
                + " values (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, userId, account, password, email, createdAt, createBy);
    }
}
