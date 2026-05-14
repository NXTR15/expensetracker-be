package com.nexstudio.expensetracker_be.enums;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    private String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
