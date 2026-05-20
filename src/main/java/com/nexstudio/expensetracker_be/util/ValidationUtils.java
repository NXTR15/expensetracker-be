package com.nexstudio.expensetracker_be.util;

import com.nexstudio.expensetracker_be.dto.request.AuthenticationRequest;
import com.nexstudio.expensetracker_be.dto.request.RegisterRequest;
import com.nexstudio.expensetracker_be.dto.request.TransactionRequest;
import com.nexstudio.expensetracker_be.enums.PaymentMethod;
import com.nexstudio.expensetracker_be.enums.Source;
import com.nexstudio.expensetracker_be.exception.UsernameValidationException;
import com.nexstudio.expensetracker_be.repository.main.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ValidationUtils {
    private final UserRepository userRepository;

    public void validateLoginRequest(AuthenticationRequest request) {
        validateUsername(request.getUsernameOrEmail());
        validatePassword(request.getPassword());
    }

    public void validateRegisterRequest(RegisterRequest request) {
        validateUsername(request.getUsername());
        validatePassword(request.getPassword());

        if (userRepository.getUser(request.getUsername().toLowerCase()) != null) {
            throw new UsernameValidationException("Username already exists");
        }
    }

    public void validateTransactionRequest(TransactionRequest request) {
        String username = request != null ? request.getUsername() : null;
        String categoryName = request != null ? request.getCategoryCode() : null;
        BigDecimal amount = request != null ? request.getAmount() : null;
        String trxDate = request != null ? request.getTrxDate() : null;
        PaymentMethod paymentMethod = request != null ? request.getPaymentMethod() : null;
        Source source = request != null ? request.getSource() : null;

        validateTransactionUsername(username);
        validateCategoryName(categoryName);
        validateAmount(amount);
        validateTrxDate(trxDate);
        validatePaymentAndSource(paymentMethod, source);
    }

    private void validateUsername(String username) {
        if (!username.matches(StaticUtils.REGEX)) {
            throw new IllegalArgumentException("Username only allows alphanumeric, underscore, and exclamation mark");
        }

        if (username.length() > StaticUtils.DEFAULT_LENGTH.get("username")) {
            throw new IllegalArgumentException("Username length must not exceed " + StaticUtils.DEFAULT_LENGTH.get("username"));
        }
    }

    private void validatePassword(String password) {
        if (!password.matches(StaticUtils.REGEX)) {
            throw new IllegalArgumentException("Password only allows alphanumeric, underscore, and exclamation mark");
        }

        if (password.length() > StaticUtils.DEFAULT_LENGTH.get("password")) {
            throw new IllegalArgumentException("Password length must not exceed " + StaticUtils.DEFAULT_LENGTH.get("password"));
        }
    }

    private void validateTransactionUsername(String username) {
        if (username == null || username.isBlank() || !username.matches(StaticUtils.REGEX) ||
                userRepository.getUser(username.toLowerCase()) == null) {
            throw new IllegalArgumentException("trx.error.001");
        }
    }

    private void validateCategoryName(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            throw new IllegalArgumentException("cat.error.001");
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("trx.error.002");
        }
    }

    private void validateTrxDate(String trxDate) {
        if (trxDate == null || trxDate.isBlank()) {
            throw new IllegalArgumentException("trx.error.004");
        }
    }

    private void validatePaymentAndSource(PaymentMethod paymentMethod, Source source) {
        if (paymentMethod == null || source == null) {
            throw new IllegalArgumentException("trx.error.003");
        }
    }
}
