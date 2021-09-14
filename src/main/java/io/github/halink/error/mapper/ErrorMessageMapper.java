package io.github.halink.error.mapper;


import io.github.halink.error.properties.ErrorProperties;

/**
 * 后面需要时在扩展
 *
 * @author Halink
 * @date 2021-09-09 15:29
 */
public class ErrorMessageMapper {
    private final ErrorProperties properties;

    public ErrorMessageMapper(ErrorProperties properties) {
        this.properties = properties;
    }

    public String getErrorMessage(Throwable exception) {
        String message = exception.getMessage();
        if (null == message) {
            message = this.properties.getDefaultErrorMessage();
        }
        return message;
    }
}
