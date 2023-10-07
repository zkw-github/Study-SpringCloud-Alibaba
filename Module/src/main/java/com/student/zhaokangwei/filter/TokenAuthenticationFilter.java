package com.student.zhaokangwei.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.student.zhaokangwei.utils.TokenUtils;
import com.student.zhaokangwei.utils.ViewUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("进入TokenAuthenticationFilter了...");

        //1.获取请求头携带的token
        String token = request.getHeader("token");
        log.info("获取到的token:" + token);
        if (token != null) {

            //2.解密token，获取标识（sign）
            Object sign = TokenUtils.verify(token);
            if (sign == null) {
                ViewUtils.print(response, "Token认证失败，请携带正确的Token令牌", null, 401);
                return;
            }
            //3.设置权限
            log.info("Token认证通过，标识为：" + sign);
            //获取当前请求者对象，后面替换的目的是将{\"role\":\"user\",\"id\":1}转换为{"role":"user","id":1}
            JSONObject userInfo = JSON.parseObject(sign.toString().replace("\\", ""));
            log.info("转换之后的用户信息：" + userInfo);

            //当前请求者的权限集合
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userInfo.get("role")));

            //在控制台打印当前请求者的所有权限（包括角色）
            for (GrantedAuthority authority : authorities) {
                log.info("当前请求者的所有权限：" + authority.getAuthority());
            }

            //SpringSecurity框架中用于保存身份认证信息的对象
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userInfo.getInteger("id"), token, authorities);
            //将上面这个储存身份认证信息的对象保存在Security上下文对象中
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        //4.放行，进行一下步认证
        log.info("TokenAuthenticationFilter放行....");
        filterChain.doFilter(request, response);
    }



}