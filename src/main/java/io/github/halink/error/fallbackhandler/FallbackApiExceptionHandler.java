package io.github.halink.error.fallbackhandler;


import io.github.halink.error.entity.ApiErrorResponse;

public interface FallbackApiExceptionHandler {
    ApiErrorResponse handle(Throwable exception);
}
