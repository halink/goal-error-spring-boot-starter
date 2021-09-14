package io.github.halink.error.handler;


import io.github.halink.error.entity.ApiErrorResponse;

public interface ApiExceptionHandler {

    boolean canHandle(Throwable exception);

    ApiErrorResponse handle(Throwable exception);
}
