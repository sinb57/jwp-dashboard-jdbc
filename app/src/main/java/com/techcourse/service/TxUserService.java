package com.techcourse.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.techcourse.domain.User;

public class TxUserService implements UserService {

    private final PlatformTransactionManager transactionManager;
    private final AppUserService userService;

    public TxUserService(final PlatformTransactionManager transactionManager, final AppUserService userService) {
        this.transactionManager = transactionManager;
        this.userService = userService;
    }

    @Override
    public User findById(final long id) {
        return userService.findById(id);
    }

    @Override
    public void insert(final User user) {
        userService.insert(user);
    }

    @Override
    public void changePassword(final long id, final String newPassword, final String createBy) {
        ifFailThenRollback(() ->
                userService.changePassword(id, newPassword, createBy)
        );
    }

    private void ifFailThenRollback(final Runnable runnable) {
        final var transactionDefinition = new DefaultTransactionDefinition();
        final var transactionStatus = transactionManager.getTransaction(transactionDefinition);
        try {
            runnable.run();
            transactionManager.commit(transactionStatus);
        } catch (final Exception e) {
            transactionManager.rollback(transactionStatus);
            throw e;
        }
    }
}