package org.lmmarise.activiti.activiti7.common.exception;

import lombok.*;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/9/23 18:18
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class BizException extends RuntimeException {
    private String errorMsg;
    private String errorCode;
    private Object errorData;
}
