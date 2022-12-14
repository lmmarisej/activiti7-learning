package org.lmmarise.activiti.activiti7.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lmmarise.activiti.activiti7.util.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.lmmarise.activiti.activiti7.common.exception.BasicBizErrorEnum.LOGIN_FAIL;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/9/23 01:45
 */
@Component("loginFailureHandler")
public class LoginFailureHandler implements AuthenticationFailureHandler {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        logger.info("登录失败");
        httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        
        httpServletResponse.getWriter()
                .write(objectMapper.writeValueAsString(
                        ApiResult.failure(LOGIN_FAIL.getErrorCode(), e.getMessage())
                ));
    }
}
