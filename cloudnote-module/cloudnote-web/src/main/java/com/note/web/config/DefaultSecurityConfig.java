package com.note.web.config;

import com.note.api.constant.HttpStatus;
import com.note.api.result.R;
import com.note.api.utils.RUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 默认安全配置
 *
 * @date 2022/11/29 20:31
 **/
@Configuration
public class DefaultSecurityConfig {

    public static class SecurityConfigAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //采取token验证，关闭csrf验证
            http.csrf().disable();
            http.sessionManagement().disable();
            //设置跨域
            http.cors();
            http
                    .exceptionHandling()
                    //权限不足处理方式
                    .accessDeniedHandler((request, response, e) ->
                            RUtils.toResponse(
                                    R.fail(HttpStatus.FORBIDDEN, e.getMessage()), response
                            )
                    )
                    //验证失败处理方式
                    .authenticationEntryPoint((request, response, e) ->
                            RUtils.toResponse(
                                    R.fail(HttpStatus.UNAUTHORIZED, e.getMessage()), response
                            )
                    );
        }

    }

}
