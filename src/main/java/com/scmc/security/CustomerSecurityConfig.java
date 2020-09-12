package com.scmc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomerSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CustomAuthenticationSuccessHandle customAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        //不需要保护的资源路径允许访问
        registry.antMatchers("/toLogin").permitAll();
        //允许跨域请求的OPTIONS请求
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();
        registry.anyRequest().authenticated().and()
                .csrf()
                .disable();
        http.formLogin().loginPage("localhost:8089").loginProcessingUrl("/authentication/form");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解除Spring-Security
        web.ignoring().mvcMatchers("/layui/**");
        web.ignoring().mvcMatchers("/img/**").antMatchers("/resources/*");
    }
}
