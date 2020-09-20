package com.scmc.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 根据源码，自定义过滤器，只需要任意权限即可访问
 */
public class CustomPermissionAuthorizationFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
        Subject subject = this.getSubject(servletRequest, servletResponse);
        String[] perms = (String[])((String[])mappedValue);
        boolean isPermitted = true;
        for (String perm : perms) {
            boolean permitted = subject.isPermitted(perm);
            if (permitted){
                return isPermitted;
            }
        }
        return false;
    }

    protected Subject getSubject(ServletRequest request, ServletResponse response) {
        return SecurityUtils.getSubject();
    }
}
