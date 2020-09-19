package com.scmc.mapper;

import com.scmc.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
public interface UserMapper extends BaseMapper<User> {
    @Select("select r.role_name from user u  left join user_role ur on u.id=ur.user_id left join role r on ur.role_id=r.id where u.username=#{username}")
    public Set<String> getRolesByUsername(@Param("username")String username);
}
