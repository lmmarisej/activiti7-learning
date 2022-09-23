package org.lmmarise.activiti.activiti7.util;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/9/23 15:49
 */
public  enum ResponseCode {
    SUCCESS("200", "成功"),
    ERROR("400", "错误");
    
    private final String code;
    private final String desc;
    
    ResponseCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }
}