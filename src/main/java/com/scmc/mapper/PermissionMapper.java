package com.scmc.mapper;

import com.scmc.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liming
 * @since 2020-09-14
 */
@Component
public interface PermissionMapper extends BaseMapper<Permission> {
    @Select("select p.permission_name from user u left join user_role  r on u.id=r.user_id left join role_permission rp on r.role_id=rp.role_id  left join permission p on rp.permission_id = p.id where u.username=#{username}")
    public Set<String> getPermissionsByUsername(String username);
}
