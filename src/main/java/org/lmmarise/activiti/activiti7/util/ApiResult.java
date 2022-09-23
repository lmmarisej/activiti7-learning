package org.lmmarise.activiti.activiti7.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/9/23 01:43
 */
@Data
@AllArgsConstructor
@Builder
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
}
