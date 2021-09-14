package io.github.halink.error.handler;


import io.github.halink.error.entity.ApiErrorResponse;
import io.github.halink.error.mapper.ErrorCodeMapper;
import io.github.halink.error.mapper.ErrorMessageMapper;
import io.github.halink.error.mapper.HttpStatusMapper;
import io.github.halink.error.properties.ErrorProperties;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 类型转换异常
 *
 * @author Halink
 * @date 2021-09-09 15:44
 */
public class TypeMismatchApiExceptionHandler extends AbstractApiExceptionHandler {

    public TypeMismatchApiExceptionHandler(ErrorProperties properties,
                                           HttpStatusMapper httpStatusMapper,
                                           ErrorCodeMapper errorCodeMapper,
                                           ErrorMessageMapper errorMessageMapper) {
        super(properties, httpStatusMapper, errorCodeMapper, errorMessageMapper);
    }

    @Override
    public boolean canHandle(Throwable exception) {
        return exception instanceof TypeMismatchException;
    }

    @Override
    public ApiErrorResponse handle(Throwable exception) {
        TypeMismatchException ex = (TypeMismatchException) exception;
        String propertyName = getPropertyName(ex);
        String format = "illegal param %s";
        return new ApiErrorResponse(getHttpStatus(exception),
                getErrorCode(exception),
                String.format(format, propertyName)
        );
    }

    private String getPropertyName(TypeMismatchException exception) {
        if (exception instanceof MethodArgumentTypeMismatchException) {
            return ((MethodArgumentTypeMismatchException) exception).getName();
        } else {
            return exception.getPropertyName();
        }
    }
}
