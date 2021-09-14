package io.github.halink.error.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiErrorResponse {
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    private final List<ApiFieldError> fieldErrors;
    /**
     * 拓展字段
     */
    private final Map<String, Object> data;

    public ApiErrorResponse(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.fieldErrors = new ArrayList<>();
        this.data = new HashMap<>();
    }

    @JsonIgnore
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<ApiFieldError> getFieldErrors() {
        return fieldErrors;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void addFieldError(ApiFieldError fieldError) {
        this.fieldErrors.add(fieldError);
    }

    public void putData(String key, Object value) {
        this.data.put(key, value);
    }
}
