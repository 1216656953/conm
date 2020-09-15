package com.scmc.mapper;

import com.scmc.entity.Role;
import com.scmc.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liming
 * @since 2020-09-14
 */
public interface UserMapper extends BaseMapper<User> {

    public Set<Role> getRolesByUserId(Long userid);
}
