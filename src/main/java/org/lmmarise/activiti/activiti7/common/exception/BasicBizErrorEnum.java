package org.lmmarise.activiti.activiti7.common.exception;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/9/23 18:20
 */
public enum BasicBizErrorEnum implements ExceptionEnum {
    OTHER("OTHER", "未知异常"),
    LOGIN_FAIL("LOGIN_FAIL", "登录失败"),
    MISS_REQUIRE_PARAM("MISS_REQUIRE_PARAM", "缺少必要参数"),
    CHECK_NO_PASS("CHECK_NO_PASS", "校验不通过，请完成必填信息"),
    USER_CONTEXT_MISS("USER_CONTEXT_MISS", "用户上下文缺失"),
    AUDITOR_CONTEXT_MISS("AUDITOR_CONTEXT_MISS", "审计上下文缺失"),
    TENANT_CONTEXT_MISS("TENANT_CONTEXT_MISS", "租户上下文缺失"),
    PERMISSION_VERIFICATION_FAILED("PERMISSION_VERIFICATION_FAILED", "功能权限验证失败"),
    TENANT_ILLEGALITY_OPERATION("TENANT_ILLEGALITY_DATA_OPERATION", "租户非法数据操作"),
    QUEUE_ERROR("QUEUE_ERROR", "消息消费失败"),
    CHECK_PARAMS_ERROR("CHECK_PARAMS_ERROR", "参数校验不通过"),
    ILLEGAL_STATE("ILLEGAL_STATE", "非法状态"),
    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "非法参数"),
    CONNECTION_NO_RESPONSE("connect_no_response", "short time request es 3 times ,no response"),
    TRY_LOCK_FAILED("TRY_LOCK_FAILED", "获取锁失败");
    
    private final String errorCode;
    private final String errorMsg;
    
    BasicBizErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    
    public String getErrorCode() {
        return this.errorCode;
    }
    
    public String getErrorMsg() {
        return this.errorMsg;
    }
}
