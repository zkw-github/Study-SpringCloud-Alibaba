package com.student.zhaokangwei.config;

import com.student.zhaokangwei.filter.TokenAuthenticationFilter;
import com.student.zhaokangwei.handler.UserAccessDeniedHandler;
import com.student.zhaokangwei.handler.UserAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * 安全配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    UserAuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    TokenAuthenticationFilter tokenAuthenticationFilter;
    @Resource
    UserAccessDeniedHandler userAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll() //设置开放API路径
                .anyRequest().authenticated()                 //对其它请求进行认证
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)     //当身份认证未通过时执行的操作
                .and()
                .exceptionHandling().accessDeniedHandler(userAccessDeniedHandler)           //当身份认证通后但权限不足时执行的操作
                .and()
                .csrf().disable()   //禁用CSRF
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //关闭Session
                .and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}