package com.nexstudio.expensetracker_be.repository;

import com.nexstudio.expensetracker_be.entity.UserEntity;

public interface UserRepository {
    void saveUser(UserEntity user);

    UserEntity getUser(String username);
}
