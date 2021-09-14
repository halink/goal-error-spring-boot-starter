package io.github.halink.error;


import io.github.halink.error.entity.ApiErrorResponse;
import io.github.halink.error.fallbackhandler.FallbackApiExceptionHandler;
import io.github.halink.error.handler.ApiExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Locale;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ErrorControllerAdvice {

    private final List<ApiExceptionHandler> handlers;
    private final FallbackApiExceptionHandler fallbackHandler;

    public ErrorControllerAdvice(List<ApiExceptionHandler> handlers,
                                 FallbackApiExceptionHandler fallbackHandler) {
        this.handlers = handlers;
        this.fallbackHandler = fallbackHandler;
        this.handlers.sort(AnnotationAwareOrderComparator.INSTANCE);
        log.info("Error Handling Spring Boot Starter active with {} handlers", this.handlers.size());
        log.info("Handlers: {}", this.handlers);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Throwable exception, WebRequest webRequest, Locale locale) {
        log.debug("webRequest: {}", webRequest);
        log.debug("locale: {}", locale);
        log.error(exception.getMessage(), exception);
        ApiErrorResponse errorResponse = null;
        for (ApiExceptionHandler handler : handlers) {
            if (handler.canHandle(exception)) {
                errorResponse = handler.handle(exception);
                break;
            }
        }
        if (errorResponse == null) {
            errorResponse = fallbackHandler.handle(exception);
        }
        return ResponseEntity.status(errorResponse.getHttpStatus())
                .body(errorResponse);
    }
}
