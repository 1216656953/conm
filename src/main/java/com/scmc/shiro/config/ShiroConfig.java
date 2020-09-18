package com.scmc.shiro.config;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/***
 * shiro配置类
 */
@SpringBootConfiguration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //认证失败的时候会以get方式跳转到这个LoginUrl路径，所以这个路径必须是get方式
        shiroFilterFactoryBean.setLoginUrl("/pub/login");
        shiroFilterFactoryBean.setSuccessUrl("/authc/success");
        shiroFilterFactoryBean.setUnauthorizedUrl("/pub/unAuth");
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(getFilterChainMap());
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(customerRealm());
        manager.setSessionManager(sessionManager());
       // manager.setCacheManager(redisCacheManager());
        return manager;
    }

    @Bean
    public CustomerRealm customerRealm() {
        CustomerRealm realm = new CustomerRealm();
        //realm.setCredentialsMatcher(credentialsMatcher());
        return realm;
    }

    @Bean
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        //双重md5
        matcher.setHashIterations(2);
        return matcher;
    }

    @Bean
    public SessionManager sessionManager() {
        CustomSessionManager sessionManager = new CustomSessionManager();
        sessionManager.setGlobalSessionTimeout(30*60*1000L);
        return sessionManager;
    }

    @Bean
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setExpire(30*60*1000);
        return cacheManager;
    }

    /**
     * 开启shiro 注解模式
     * 可以在controller中的方法前加上注解
     * 如 @RequiresPermissions("userInfo:add")
     * @return
     */
   /* @Bean
    public  LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public  AuthorizationAttributeSourceAdvisor getDefaultAdvisorAutoProxyCreator(){
        AuthorizationAttributeSourceAdvisor proxyCreator = new AuthorizationAttributeSourceAdvisor();
        proxyCreator.setSecurityManager(securityManager());
        return proxyCreator;
    }*/

    private Map<String, String> getFilterChainMap() {
        Map<String, String> map = new LinkedHashMap<>();
        //退出过滤器
        map.put("/logout", "logout");
        //匿名过滤器,pub下的路径都不用验权
        map.put("/pub", "anon");
        //登录才能访问的过滤器
        map.put("/authc/**", "authc");
        //管理员才能访问
        map.put("/admin/**", "roles[admin]");
        //具有合同查看权限才能访问
        map.put("/conm/view", "perms[view]");;
        //通过认证才能访问
        map.put("**", "authc");
        return map;
    }
}
