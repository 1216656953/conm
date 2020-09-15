package com;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scmc.entity.User;
import com.scmc.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MybatisMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void testSelect() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username","liming");
        User user = userMapper.selectOne(wrapper);
    }
}
