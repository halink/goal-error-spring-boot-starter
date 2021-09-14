package io.github.halink.error.handler;


import io.github.halink.error.mapper.ErrorCodeMapper;
import io.github.halink.error.mapper.ErrorMessageMapper;
import io.github.halink.error.mapper.HttpStatusMapper;
import io.github.halink.error.properties.ErrorProperties;
import org.springframework.http.HttpStatus;

public abstract class AbstractApiExceptionHandler implements ApiExceptionHandler {
    protected final ErrorProperties properties;
    protected final HttpStatusMapper httpStatusMapper;
    protected final ErrorCodeMapper errorCodeMapper;
    protected final ErrorMessageMapper errorMessageMapper;

    public AbstractApiExceptionHandler(ErrorProperties properties,
                                       HttpStatusMapper httpStatusMapper,
                                       ErrorCodeMapper errorCodeMapper,
                                       ErrorMessageMapper errorMessageMapper) {
        this.properties = properties;
        this.httpStatusMapper = httpStatusMapper;
        this.errorCodeMapper = errorCodeMapper;
        this.errorMessageMapper = errorMessageMapper;
    }

    protected HttpStatus getHttpStatus(Throwable exception) {
        return httpStatusMapper.getHttpStatus(exception);
    }

    protected String getErrorCode(Throwable exception) {
        return errorCodeMapper.getErrorCode(exception);
    }

    protected String getErrorMessage(Throwable exception) {
        String errorMessage = errorMessageMapper.getErrorMessage(exception);
        HttpStatus httpStatus = getHttpStatus(exception);
        if (!properties.getServerError().isShow() & HttpStatus.INTERNAL_SERVER_ERROR == httpStatus) {
            errorMessage = properties.getServerError().getErrorMessage();
        }
        return errorMessage;
    }
}
