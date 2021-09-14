package io.github.halink.error.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorResult {
    private final String code;
    private final String message;
}
