package com.nexstudio.expensetracker_be.service;

import com.nexstudio.expensetracker_be.dto.request.RegisterRequest;

public interface UserService {
    void register(RegisterRequest request);
}
