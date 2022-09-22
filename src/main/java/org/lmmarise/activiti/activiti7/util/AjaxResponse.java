package org.lmmarise.activiti.activiti7.util;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/9/23 01:43
 */
public class AjaxResponse {
    private Integer status;
    private String msg;
    private Object obj;
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public Object getObj() {
        return obj;
    }
    
    public void setObj(Object obj) {
        this.obj = obj;
    }
    
    private AjaxResponse(Integer status, String msg, Object obj) {
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }
    
    public static AjaxResponse AjaxData(Integer status, String msg, Object obj) {
        return new AjaxResponse(status, msg, obj);
    }
}
