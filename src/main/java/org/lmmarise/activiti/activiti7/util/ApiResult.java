package org.lmmarise.activiti.activiti7.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/9/23 01:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult<T> implements Serializable  {
    private boolean success;
    private String errorCode;
    private Object errorData;
    private T data;
    private String message;
    
    public ApiResult(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
    
    public static <T> ApiResult<Object> success(T data) {
        return success(data, null);
    }
    
    public static <T> ApiResult<T> success(T data, String message) {
        ApiResult<T> result = new ApiResult<>();
        result.setData(data);
        result.setMessage(message);
        result.setSuccess(true);
        return result;
    }
    
    public static <T> ApiResult<T> failure(String errorCode, String message) {
        return failure(errorCode, message, null);
    }
    
    public static <T> ApiResult<T> failure(String errorCode, String message, Object errorData) {
        ApiResult<T> result = new ApiResult<>();
        result.setErrorCode(errorCode);
        result.setErrorData(errorData);
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }
}
