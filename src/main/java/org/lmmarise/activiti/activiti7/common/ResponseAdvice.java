package org.lmmarise.activiti.activiti7.common;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.lmmarise.activiti.activiti7.common.annotation.IgnoredControllerAdvice;
import org.lmmarise.activiti.activiti7.common.exception.BasicBizErrorEnum;
import org.lmmarise.activiti.activiti7.common.exception.BizException;
import org.lmmarise.activiti.activiti7.util.ApiResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/9/23 16:13
 */
@RestControllerAdvice("org.lmmarise.activiti")
@Slf4j
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    
    private final Gson gson = new Gson();
    
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return Objects.isNull(Objects.requireNonNull(returnType.getMethod()).getAnnotation(IgnoredControllerAdvice.class));
    }
    
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ApiResult) {
            return body;
        } else {
            ApiResult<Object> ret = ApiResult.success(body);
            if (body instanceof String) {
                response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                return gson.toJson(ret);
            }
            return ret;
        }
    }
    
    @ResponseBody
    @ExceptionHandler({Throwable.class})
    public ApiResult<Object> handleException(Throwable ex) {
        log.error("handleException", ex);
        return ApiResult.failure(BasicBizErrorEnum.ILLEGAL_ARGUMENT.getErrorCode(), ex.getMessage());
    }
    
    @ResponseBody
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ApiResult handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.debug("handleBizException", ex);
        return ApiResult.failure(BasicBizErrorEnum.MISS_REQUIRE_PARAM.getErrorCode(),
                BasicBizErrorEnum.MISS_REQUIRE_PARAM.getErrorMsg() + "：" + ex.getMessage());
    }
    
    @ResponseBody
    @ExceptionHandler({IllegalArgumentException.class})
    public ApiResult handleValidException(IllegalArgumentException e) {
        log.debug("handleBizException", e);
        return ApiResult.failure(BasicBizErrorEnum.ILLEGAL_ARGUMENT.getErrorCode(), e.getMessage());
    }
    
    @ResponseBody
    @ExceptionHandler({IllegalStateException.class})
    public ApiResult handleValidException(IllegalStateException e) {
        log.debug("handleBizException", e);
        return ApiResult.failure(BasicBizErrorEnum.ILLEGAL_STATE.getErrorCode(), e.getMessage());
    }
    
    @ResponseBody
    @ExceptionHandler({BizException.class})
    public ApiResult handleBizBasicException(BizException ex) {
        log.debug("handleBizException", ex);
        if (Objects.nonNull(ex.getErrorData())) {
            return ApiResult.failure(ex.getErrorCode(), ex.getMessage(), ex.getErrorData());
        } else {
            return ApiResult.failure(ex.getErrorCode(), ex.getErrorMsg());
        }
    }
    
}
