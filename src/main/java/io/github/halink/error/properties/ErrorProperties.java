package io.github.halink.error.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("halink.error")
public class ErrorProperties {
    private boolean enabled = true;

    /**
     * 默认状态码
     */
    private HttpStatus defaultHttpStatus = HttpStatus.BAD_REQUEST;

    /**
     * 默认message
     */
    private String defaultErrorMessage = "请稍候重试";

    /**
     * 服务器异常
     */
    private ServerError serverError = new ServerError();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ServerError getServerError() {
        return serverError;
    }

    public void setServerError(ServerError serverError) {
        this.serverError = serverError;
    }

    public HttpStatus getDefaultHttpStatus() {
        return defaultHttpStatus;
    }

    public void setDefaultHttpStatus(HttpStatus defaultHttpStatus) {
        this.defaultHttpStatus = defaultHttpStatus;
    }

    public String getDefaultErrorMessage() {
        return defaultErrorMessage;
    }

    public void setDefaultErrorMessage(String defaultErrorMessage) {
        this.defaultErrorMessage = defaultErrorMessage;
    }

    @Data
    public static class ServerError {
        /**
         * 服务器异常信息是否展示 默认: false
         */
        private boolean show = false;
        /**
         * 不展示时，展示内容
         */
        private String errorMessage = "请稍候重试";
    }
}
