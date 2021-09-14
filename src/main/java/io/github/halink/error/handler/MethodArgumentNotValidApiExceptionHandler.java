package io.github.halink.error.handler;


import io.github.halink.error.entity.ApiErrorResponse;
import io.github.halink.error.entity.ApiFieldError;
import io.github.halink.error.mapper.ErrorCodeMapper;
import io.github.halink.error.mapper.ErrorMessageMapper;
import io.github.halink.error.mapper.HttpStatusMapper;
import io.github.halink.error.properties.ErrorProperties;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * json参数
 *
 * @author Halink
 * @date 2021-09-09 15:44
 */
public class MethodArgumentNotValidApiExceptionHandler extends AbstractApiExceptionHandler {

    public MethodArgumentNotValidApiExceptionHandler(ErrorProperties properties,
                                                     HttpStatusMapper httpStatusMapper,
                                                     ErrorCodeMapper errorCodeMapper,
                                                     ErrorMessageMapper errorMessageMapper) {
        super(properties, httpStatusMapper, errorCodeMapper, errorMessageMapper);
    }

    @Override
    public boolean canHandle(Throwable exception) {
        return exception instanceof BindException;
    }

    @Override
    public ApiErrorResponse handle(Throwable exception) {

        MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;
        ApiErrorResponse response = new ApiErrorResponse(getHttpStatus(exception),
                getErrorCode(exception), "invalid parameter");
        BindingResult bindingResult = ex.getBindingResult();
        if (bindingResult.hasFieldErrors()) {
            bindingResult.getFieldErrors().stream()
                    .map(fieldError -> new ApiFieldError(fieldError.getField(), fieldError.getDefaultMessage()))
                    .forEach(response::addFieldError);
        }
        return response;
    }
}
