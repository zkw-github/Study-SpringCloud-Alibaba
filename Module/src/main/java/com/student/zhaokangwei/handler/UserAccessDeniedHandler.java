package com.student.zhaokangwei.handler;

import com.student.zhaokangwei.utils.ViewUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 当身份认证通后但权限不足时执行的操作
 */
@Component
public class UserAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) {

        ViewUtils.print(response, "请求该接口权限不足", null, 403);

    }
}