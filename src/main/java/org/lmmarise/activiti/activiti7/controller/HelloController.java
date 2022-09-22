package org.lmmarise.activiti.activiti7.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/9/22 23:49
 */
@RestController
public class HelloController {
    
    @GetMapping("/hello")
    public String say() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return "hello " + userName;
    }
    
}
