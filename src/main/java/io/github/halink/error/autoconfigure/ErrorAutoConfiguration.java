package io.github.halink.error.autoconfigure;

import io.github.halink.error.ErrorControllerAdvice;
import io.github.halink.error.fallbackhandler.DefaultFallbackApiExceptionHandler;
import io.github.halink.error.fallbackhandler.FallbackApiExceptionHandler;
import io.github.halink.error.handler.*;
import io.github.halink.error.mapper.ErrorCodeMapper;
import io.github.halink.error.mapper.ErrorMessageMapper;
import io.github.halink.error.mapper.HttpStatusMapper;
import io.github.halink.error.properties.ErrorProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(ErrorProperties.class)
@ConditionalOnProperty(value = "error.handling.enabled", matchIfMissing = true)
public class ErrorAutoConfiguration {


    @Bean
    public ErrorControllerAdvice errorHandlingControllerAdvice(
            List<ApiExceptionHandler> handlers,
            FallbackApiExceptionHandler fallbackApiExceptionHandler) {
        return new ErrorControllerAdvice(handlers,
                fallbackApiExceptionHandler);
    }

    @Bean
    public HttpStatusMapper httpStatusMapper(ErrorProperties properties) {
        return new HttpStatusMapper(properties);
    }

    @Bean
    public ErrorCodeMapper errorCodeMapper() {
        return new ErrorCodeMapper();
    }

    @Bean
    public ErrorMessageMapper errorMessageMapper(ErrorProperties properties) {
        return new ErrorMessageMapper(properties);
    }

    @Bean
    public FallbackApiExceptionHandler defaultHandler(ErrorProperties properties,
                                                      HttpStatusMapper httpStatusMapper,
                                                      ErrorCodeMapper errorCodeMapper,
                                                      ErrorMessageMapper errorMessageMapper) {
        return new DefaultFallbackApiExceptionHandler(properties, httpStatusMapper, errorCodeMapper, errorMessageMapper);
    }

    @Bean
    public TypeMismatchApiExceptionHandler typeMismatchApiExceptionHandler(ErrorProperties properties,
                                                                           HttpStatusMapper httpStatusMapper,
                                                                           ErrorCodeMapper errorCodeMapper,
                                                                           ErrorMessageMapper errorMessageMapper) {
        return new TypeMismatchApiExceptionHandler(properties, httpStatusMapper, errorCodeMapper, errorMessageMapper);
    }

    @Bean
    public ConstraintViolationApiExceptionHandler constraintViolationApiExceptionHandler(ErrorProperties properties,
                                                                                         HttpStatusMapper httpStatusMapper,
                                                                                         ErrorCodeMapper errorCodeMapper,
                                                                                         ErrorMessageMapper errorMessageMapper) {
        return new ConstraintViolationApiExceptionHandler(properties, httpStatusMapper, errorCodeMapper, errorMessageMapper);
    }

    @Bean
    public HttpMessageNotReadableApiExceptionHandler httpMessageNotReadableApiExceptionHandler(ErrorProperties properties,
                                                                                               HttpStatusMapper httpStatusMapper,
                                                                                               ErrorCodeMapper errorCodeMapper,
                                                                                               ErrorMessageMapper errorMessageMapper) {
        return new HttpMessageNotReadableApiExceptionHandler(properties, httpStatusMapper, errorCodeMapper, errorMessageMapper);
    }

    @Bean
    public MethodArgumentNotValidApiExceptionHandler methodArgumentNotValidApiExceptionHandler(ErrorProperties properties,
                                                                                               HttpStatusMapper httpStatusMapper,
                                                                                               ErrorCodeMapper errorCodeMapper,
                                                                                               ErrorMessageMapper errorMessageMapper) {
        return new MethodArgumentNotValidApiExceptionHandler(properties, httpStatusMapper, errorCodeMapper, errorMessageMapper);
    }
}
