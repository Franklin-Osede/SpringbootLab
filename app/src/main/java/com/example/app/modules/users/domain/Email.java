package com.example.app.modules.users.domain;

import com.example.app.shared.domain.ValueObject;
import java.util.regex.Pattern;

/**
 * Email value object representing a valid email address.
 * 
 * This class encapsulates email validation logic and ensures
 * that only valid email addresses are accepted.
 */
public class Email extends ValueObject<String> {

    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public Email(String value) {
        super(value);
        validateEmail(value);
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        String trimmedEmail = email.trim().toLowerCase();
        
        if (!EMAIL_PATTERN.matcher(trimmedEmail).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }

        if (trimmedEmail.length() > 254) {
            throw new IllegalArgumentException("Email too long: " + email);
        }
    }

    /**
     * Get the email value in lowercase.
     */
    public String getValue() {
        return value.toLowerCase();
    }

    /**
     * Get the domain part of the email.
     */
    public String getDomain() {
        return value.substring(value.indexOf('@') + 1);
    }

    /**
     * Get the local part of the email (before @).
     */
    public String getLocalPart() {
        return value.substring(0, value.indexOf('@'));
    }

    /**
     * Check if this is a common email provider.
     */
    public boolean isCommonProvider() {
        String domain = getDomain().toLowerCase();
        return domain.equals("gmail.com") || 
               domain.equals("yahoo.com") || 
               domain.equals("hotmail.com") ||
               domain.equals("outlook.com");
    }

    @Override
    public String toString() {
        return getValue();
    }
}
