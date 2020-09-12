/*
package com.scmc.conm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.background.modules.security.bean.SecurityUser;
import com.background.modules.user.bean.User;
import com.background.modules.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        if(user==null){
            throw new BadCredentialsException("用户不存在");
        }
        if(user.getStatus()==0){
            throw new BadCredentialsException("用户被禁用");
        }
        SecurityUser userInfo = new SecurityUser();
        BeanUtils.copyProperties(user,userInfo);
        return userInfo;
    }
}*/
