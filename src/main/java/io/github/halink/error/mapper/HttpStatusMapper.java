package io.github.halink.error.mapper;


import io.github.halink.error.properties.ErrorProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * 注释 > ResponseStatusException | DefaultHttpStatus
 * <p>
 * DefaultHttpStatus 可通过 halink.error.default-http-status 指定
 *
 * @author Halink
 * @date 2021-09-09 15:29
 */
public class HttpStatusMapper {
    private final ErrorProperties properties;

    public HttpStatusMapper(ErrorProperties properties) {
        this.properties = properties;
    }

    public HttpStatus getHttpStatus(Throwable exception) {
        ResponseStatus responseStatus = AnnotationUtils.getAnnotation(exception.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            return responseStatus.value();
        }
        if (exception instanceof ResponseStatusException) {
            return ((ResponseStatusException) exception).getStatus();
        }
        return properties.getDefaultHttpStatus();
    }

}
