package com.example.app.after.domain.valueobjects;

import com.example.app.shared.domain.ValueObject;

import java.util.regex.Pattern;

/**
 * ✅ EMAIL VALUE OBJECT - AFTER REFACTORING
 * 
 * IMPROVEMENTS IMPLEMENTED:
 * - Immutable value object
 * - Domain validation with regex
 * - Type safety
 * - Follows DDD principles
 */
public class Email extends ValueObject<String> {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    public Email(String value) {
        super(value);
        validate(value);
    }

    /**
     * ✅ IMPROVEMENT: Domain validation with regex
     */
    private void validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + value);
        }
        
        if (value.length() > 255) {
            throw new IllegalArgumentException("Email cannot exceed 255 characters");
        }
    }

    /**
     * ✅ IMPROVEMENT: Get the string value
     */
    public String getValue() {
        return value();
    }

    /**
     * ✅ IMPROVEMENT: Get domain part of email
     */
    public String getDomain() {
        return value().substring(value().indexOf("@") + 1);
    }

    /**
     * ✅ IMPROVEMENT: Get local part of email
     */
    public String getLocalPart() {
        return value().substring(0, value().indexOf("@"));
    }
}
