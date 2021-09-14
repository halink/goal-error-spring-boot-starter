# Getting Started

统一异常处理 适用于servlet服务

> 参考: Error Handling Spring Boot Starter
>
> https://github.com/wimdeblauwe/error-handling-spring-boot-starter

## Dependency

```xml
<dependency>
    <groupId>io.github.halink</groupId>
    <artifactId>goal-error-spring-boot-starter</artifactId>
    <version>0.1</version>
</dependency>
```

## Usage

### 1. 直接抛自定义异常

**HttpStatus优先级:**  `@ResponseStatus > ResponseStatusException > default-http-status`

**code优先级:** `@ResponseErrorCode`

**message优先级:** `exception.message > default-error-message`

```java
// 自定义异常
@ResponseStatus(HttpStatus.BAD_REQUEST)
@ResponseErrorCode("40001")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("Could not find user with id " + userId);
    }
}
```

### 2. 需要特殊处理的异常可自定义ExceptionHandler（两种方式）

```java

/**
 * 不受properties配置影响
 */
@Component
public class CustomExceptionApiExceptionHandler implements ApiExceptionHandler {
    @Override
    public boolean canHandle(Throwable exception) {
        return exception instanceof CustomException;
    }

    @Override
    public ApiErrorResponse handle(Throwable exception) {
        CustomException customException = (CustomException) exception;

        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "MY_CUSTOM_EXCEPTION",
                exception.getMessage());
        Throwable cause = customException.getCause();
        Map<String, Object> nestedCause = new HashMap<>();
        nestedCause.put("code", "CAUSE");
        nestedCause.put("message", cause.getMessage());
        response.putData("cause", nestedCause);
        return response;
    }
}

/**
 * 受properties配置影响
 */
@Component
public class CustomExceptionHandler extends AbstractApiExceptionHandler {

    public CustomExceptionHandler(ErrorProperties properties, HttpStatusMapper httpStatusMapper, ErrorCodeMapper errorCodeMapper, ErrorMessageMapper errorMessageMapper) {
        super(properties, httpStatusMapper, errorCodeMapper, errorMessageMapper);
    }

    @Override
    public boolean canHandle(Throwable exception) {
        return exception instanceof CustomException;
    }

    @Override
    public ApiErrorResponse handle(Throwable exception) {
        return new ApiErrorResponse(getHttpStatus(exception),
                getErrorCode(exception),
                "json 格式异常"
        );
    }
}
```

3. 响应格式

```json
{
  "code": "VALIDATION_FAILED",
  "message": "Validation failed for object='exampleRequestBody'. Error count: 4",
  "fieldErrors": [
    {
      "property": "name",
      "message": "size must be between 10 and 2147483647"
    }
  ],
  "data": {
    "code": "ValidCustomer",
    "message": "UserAlreadyExists"
  }
}
```

## Properties

```yaml
halink:
  error:
    enabled: true
    # 默认 HttpStatus
    default-http-status: bad_request
    # 默认 error message
    default-error-message: 请稍候重试
    # 针对 HttpStatus.INTERNAL_SERVER_ERROR
    server-error:
      # 是否展示详细信息 默认: false
      show: false
      # 不展示详细信息时，展示内容
      error-message: 请稍候重试
```