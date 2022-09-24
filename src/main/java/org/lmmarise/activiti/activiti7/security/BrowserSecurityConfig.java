package org.lmmarise.activiti.activiti7.security;

import org.lmmarise.activiti.activiti7.mapper.UserInfoBeanMapper;
import org.lmmarise.activiti.activiti7.pojo.UserInfoBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/9/23 01:40
 */
@Configuration
@EnableWebSecurity
public class BrowserSecurityConfig {
    
    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    
    @Resource
    private LoginFailureHandler loginFailureHandler;
    
    @Resource
    private UserInfoBeanMapper mapper;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/layuimini/page/login-1.html")
                .loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                //.defaultSuccessUrl("/hello")
                //.successForwardUrl("success.html")
                //.failureForwardUrl("failure.html")
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/layuimini/page/login-1.html").permitAll()
                .antMatchers("/layuimini/css/**").permitAll()
                .antMatchers("/layuimini/font/**").permitAll()
                .antMatchers("/layuimini/api/**").permitAll()
                .antMatchers("/layuimini/js/**").permitAll()
                .antMatchers("/layuimini/lib/**").permitAll()
                .antMatchers("/bpmnjs/**").permitAll()
//                .anyRequest().permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable();
        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UserInfoBean userInfoBean = mapper.selectByUsername(username);
            if (userInfoBean == null) {
                throw new UsernameNotFoundException("用户不存在！");
            }
            return userInfoBean;
        };
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
