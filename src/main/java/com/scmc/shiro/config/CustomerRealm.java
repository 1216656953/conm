package com.scmc.shiro.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scmc.entity.User;
import com.scmc.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

//自定义realm，基本逻辑都是根据用户名，自己去查找数据库，查出对应的权限，角色，密码
public class CustomerRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;
    /**
     * 权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Object username = principalCollection.getPrimaryPrincipal();
        //自己查询数据库，查询这个usernam下所有的角色和权限
        Set<String> permissions = new HashSet<>();
        permissions.add("conm:add");
        permissions.add("conm:delete");
        Set<String> roles = new HashSet<>();
        roles.add("user");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permissions);
        info.setRoles(roles);
        return info;
    }

    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //自己查询数据库，查询这个username的密码
        Object username = authenticationToken.getPrincipal();
        String pwd = "";
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);
        if (user == null|| StringUtils.isEmpty(user.getPassword())) {
           return null;
        }
        pwd = user.getPassword();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, pwd, this.getName());
        return info;
    }
}
