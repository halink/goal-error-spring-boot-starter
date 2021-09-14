package io.github.halink.error.handler;


import io.github.halink.error.entity.ApiErrorResponse;
import io.github.halink.error.entity.ApiFieldError;
import io.github.halink.error.mapper.ErrorCodeMapper;
import io.github.halink.error.mapper.ErrorMessageMapper;
import io.github.halink.error.mapper.HttpStatusMapper;
import io.github.halink.error.properties.ErrorProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

/**
 * 普通参数
 *
 * @author Halink
 * @date 2021-09-09 15:43
 */
public class ConstraintViolationApiExceptionHandler extends AbstractApiExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConstraintViolationApiExceptionHandler.class);


    public ConstraintViolationApiExceptionHandler(ErrorProperties properties,
                                                  HttpStatusMapper httpStatusMapper,
                                                  ErrorCodeMapper errorCodeMapper,
                                                  ErrorMessageMapper errorMessageMapper) {
        super(properties, httpStatusMapper, errorCodeMapper, errorMessageMapper);
    }

    @Override
    public boolean canHandle(Throwable exception) {
        return exception instanceof ConstraintViolationException;
    }

    @Override
    public ApiErrorResponse handle(Throwable exception) {
        ConstraintViolationException ex = (ConstraintViolationException) exception;
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        ApiErrorResponse response = new ApiErrorResponse(getHttpStatus(exception),
                getErrorCode(exception), "invalid parameter");
        violations.forEach(constraintViolation -> {
            Optional<Path.Node> leafNode = getLeafNode(constraintViolation.getPropertyPath());
            if (leafNode.isPresent()) {
                Path.Node node = leafNode.get();
                ElementKind elementKind = node.getKind();
                if (elementKind == ElementKind.PROPERTY) {
                    response.addFieldError(new ApiFieldError(node.toString(),
                            constraintViolation.getMessage()));
                } else if (elementKind == ElementKind.BEAN) {
                    response.addFieldError(new ApiFieldError(node.toString(),
                            constraintViolation.getMessage()));
                } else if (elementKind == ElementKind.PARAMETER) {
                    response.addFieldError(new ApiFieldError(node.toString(),
                            constraintViolation.getMessage()));
                } else {
                    LOGGER.warn("Unable to convert constraint violation with element kind {}: {}", elementKind, constraintViolation);
                }
            } else {
                LOGGER.warn("Unable to convert constraint violation: {}", constraintViolation);
            }
        });
        return response;
    }

    private Optional<Path.Node> getLeafNode(Path path) {
        return StreamSupport.stream(path.spliterator(), false).reduce((a, b) -> b);
    }

}
