package io.github.halink.error.handler;


import io.github.halink.error.entity.ApiErrorResponse;
import io.github.halink.error.mapper.ErrorCodeMapper;
import io.github.halink.error.mapper.ErrorMessageMapper;
import io.github.halink.error.mapper.HttpStatusMapper;
import io.github.halink.error.properties.ErrorProperties;
import org.springframework.http.converter.HttpMessageNotReadableException;

/**
 * json格式异常
 *
 * @author Halink
 * @date 2021-09-09 15:44
 */
public class HttpMessageNotReadableApiExceptionHandler extends AbstractApiExceptionHandler {
    public HttpMessageNotReadableApiExceptionHandler(ErrorProperties properties,
                                                     HttpStatusMapper httpStatusMapper,
                                                     ErrorCodeMapper errorCodeMapper,
                                                     ErrorMessageMapper errorMessageMapper) {
        super(properties, httpStatusMapper, errorCodeMapper, errorMessageMapper);
    }

    @Override
    public boolean canHandle(Throwable exception) {
        return exception instanceof HttpMessageNotReadableException;
    }

    @Override
    public ApiErrorResponse handle(Throwable exception) {
        return new ApiErrorResponse(getHttpStatus(exception),
                getErrorCode(exception),
                getErrorMessage(exception)
        );
    }

}
