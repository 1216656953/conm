package com.scmc.shiro.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scmc.entity.User;
import com.scmc.mapper.PermissionMapper;
import com.scmc.mapper.RoleMapper;
import com.scmc.mapper.UserMapper;
import org.apache.commons.beanutils.BeanUtils;
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
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    /**
     * 权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Object principal =  principalCollection.getPrimaryPrincipal();
        //自己查询数据库，查询这个usernam下所有的角色和权限
        User user = new User();
        try {
            //springboot热部署原因，上面不能强转成User类型，需要拷贝一下
            //两个参数不能写反，如果写反的话就会抛org.crazycake.shiro.exception.PrincipalIdNullException
            BeanUtils.copyProperties(user,principal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<String> rolesByUsername = roleMapper.getRolesByUsername(user.getUsername());
        Set<String> permissions = permissionMapper.getPermissionsByUsername(user.getUsername());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permissions);
        info.setRoles(rolesByUsername);
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
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username.toString());
        User user = userMapper.selectOne(wrapper);
        if (user==null)return null;
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
        return info;
    }
}
