package com.nexstudio.expensetracker_be.enums;

public enum UserRole {
    ROLE_USER("USER");

    private String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
