package io.github.halink.error.fallbackhandler;


import io.github.halink.error.entity.ApiErrorResponse;
import io.github.halink.error.mapper.ErrorCodeMapper;
import io.github.halink.error.mapper.ErrorMessageMapper;
import io.github.halink.error.mapper.HttpStatusMapper;
import io.github.halink.error.properties.ErrorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class DefaultFallbackApiExceptionHandler implements FallbackApiExceptionHandler {

    private final ErrorProperties errorProperties;
    private final HttpStatusMapper httpStatusMapper;
    private final ErrorCodeMapper errorCodeMapper;
    private final ErrorMessageMapper errorMessageMapper;

    public DefaultFallbackApiExceptionHandler(ErrorProperties errorProperties,
                                              HttpStatusMapper httpStatusMapper,
                                              ErrorCodeMapper errorCodeMapper,
                                              ErrorMessageMapper errorMessageMapper) {
        this.errorProperties = errorProperties;
        this.httpStatusMapper = httpStatusMapper;
        this.errorCodeMapper = errorCodeMapper;
        this.errorMessageMapper = errorMessageMapper;
    }

    @Override
    public ApiErrorResponse handle(Throwable exception) {
        HttpStatus statusCode = httpStatusMapper.getHttpStatus(exception);
        String errorCode = errorCodeMapper.getErrorCode(exception);
        String errorMessage = errorMessageMapper.getErrorMessage(exception);
        if (!errorProperties.getServerError().isShow() & HttpStatus.INTERNAL_SERVER_ERROR == statusCode) {
            errorMessage = errorProperties.getServerError().getErrorMessage();
        }
        return new ApiErrorResponse(statusCode, errorCode, errorMessage);
    }
}
