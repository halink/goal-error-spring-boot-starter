package io.github.halink.error.mapper;


import io.github.halink.error.annotation.ResponseErrorCode;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Locale;

/**
 * 注释 > 类名
 *
 * @author Halink
 * @date 2021-09-09 15:29
 */
public class ErrorCodeMapper {

    public String getErrorCode(Throwable exception) {
        Class<? extends Throwable> exceptionClass = exception.getClass();
        ResponseErrorCode errorCodeAnnotation = AnnotationUtils.getAnnotation(exceptionClass, ResponseErrorCode.class);
        if (errorCodeAnnotation != null) {
            return errorCodeAnnotation.value();
        }
        String exceptionClassName = exceptionClass.getSimpleName();
        String result = exceptionClassName.replaceFirst("Exception$", "");
        result = result.replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase(Locale.ENGLISH);
        return result;
    }
}
