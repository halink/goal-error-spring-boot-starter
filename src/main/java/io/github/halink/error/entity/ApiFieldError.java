package io.github.halink.error.entity;

public class ApiFieldError {
    private final String property;
    private final String message;

    public ApiFieldError(String property, String message) {
        this.property = property;
        this.message = message;
    }

    public String getProperty() {
        return property;
    }

    public String getMessage() {
        return message;
    }

}
