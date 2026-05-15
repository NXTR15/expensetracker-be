package com.nexstudio.expensetracker_be.service;

import com.nexstudio.expensetracker_be.dto.request.AuthenticationRequest;
import com.nexstudio.expensetracker_be.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);
}
