package com.nexstudio.expensetracker_be.repository.main;

import com.nexstudio.expensetracker_be.entity.UserEntity;

public interface UserRepository {
    void saveUser(UserEntity user);

    UserEntity getUser(String username);

    UserEntity getUserByUsernameOrEmail(String usernameOrEmail);
}
