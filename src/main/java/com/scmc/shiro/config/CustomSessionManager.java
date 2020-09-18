package com.scmc.shiro.config;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 自定义
 */
public class CustomSessionManager extends DefaultWebSessionManager {

    public CustomSessionManager(){
        super();
    }

    /***
     * 传统的web应用是在session中获取sessionId的，前后端分离的应用是无状态的，所以需要自定义获取sessionId,在请求头定义token即为sessionId，每次请求都必须携带
     * @param request
     * @param response
     * @return
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String sessionId = WebUtils.toHttp(request).getHeader("token");
        System.out.println("sessionid="+sessionId);
        //第一次请求没有携带sessionId,那么就分配一个
        if (sessionId != null) {
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        return sessionId;
        }else{
            return super.getSessionId(request,response);
        }
    }

}
