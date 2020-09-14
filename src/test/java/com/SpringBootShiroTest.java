package com;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringBootShiroTest  {
    DefaultSecurityManager manager = new DefaultSecurityManager();
    SimpleAccountRealm realm = new SimpleAccountRealm();
    @Before
    public void init(){
        //1.构建shiro环境
        realm.addAccount("liming","liming");
        manager.setRealm(realm);
    }

    /**
     * 测试登录
     */
    @Test
    public void loginTest(){
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("liming","liming");
        subject.login(token);
        System.out.println("登录结果："+subject.isAuthenticated());
    }
    /*
    *测试角色
     */
    @Test
    public void roleTest(){
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("liming","liming");
        subject.login(token);
        System.out.println("登录结果："+subject.isAuthenticated());
        System.out.println("是否管理员："+subject.hasRole("root"));
        System.out.println("用户名："+subject.getPrincipal());
        subject.logout();
        System.out.println("登出后是否有权限："+subject.isAuthenticated());
    }

    /**
     * 从ini文件获取SecurityManager环境
     */
    @Test
    public void roleAuthTest(){
        //工厂模式+shiro.ini方式初始化SecurityManager
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager manager = factory.getInstance();
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("liming","liming");
        subject.login(token);
        System.out.println("登录结果："+subject.isAuthenticated());
        System.out.println("是否管理员："+subject.hasRole("root"));
        System.out.println("是否普通用户："+subject.hasRole("user"));
        System.out.println("用户名："+subject.getPrincipal());
        System.out.println("是否有删除权限："+subject.isPermitted("conm:delete"));
        System.out.println("是否有添加权限："+subject.isPermitted("conm:add"));
        subject.logout();
        System.out.println("登出后是否有权限："+subject.isAuthenticated());
    }

    /**
     * 从ini文件获取jdbcRealm的SecurityManager环境
     */
    @Test
    public void JdbcRealmRoleAuthTest(){
        //工厂模式+shiro.ini方式初始化SecurityManager
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:jdbcRealm.ini");
        SecurityManager manager = factory.getInstance();
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("liming","liming");
        subject.login(token);
        System.out.println("登录结果："+subject.isAuthenticated());
        System.out.println("是否管理员："+subject.hasRole("root"));
        System.out.println("是否普通用户："+subject.hasRole("user"));
        System.out.println("用户名："+subject.getPrincipal());
        System.out.println("是否有删除权限："+subject.isPermitted("conm:delete"));
        System.out.println("是否有添加权限："+subject.isPermitted("conm:add"));
        System.out.println("是否有sm添加权限："+subject.isPermitted("sm:add"));
        subject.logout();
        System.out.println("登出后是否有权限："+subject.isAuthenticated());

        UsernamePasswordToken rootToken = new UsernamePasswordToken("admin","admin");
        subject.login(rootToken);
        System.out.println("登录结果："+subject.isAuthenticated());
        System.out.println("是否管理员："+subject.hasRole("root"));
        System.out.println("是否普通用户："+subject.hasRole("user"));
        System.out.println("用户名："+subject.getPrincipal());
        System.out.println("是否有删除权限："+subject.isPermitted("conm:delete"));
        System.out.println("是否有添加权限："+subject.isPermitted("conm:add"));
        subject.logout();
        System.out.println("登出后是否有权限："+subject.isAuthenticated());
    }



    /**
     * 手动代码方式建立连接池，构建获取SecurityManager环境
     */
    @Test
    public void jdbdRelmAuthTest(){
        //连接池方式初始化SecurityManager
        DruidDataSource source = new DruidDataSource();
        source.setUrl("jdbc:mysql://localhost:3306/cq-sys?useUnicode=true&characterEncoding=utf8&useSSL=false");
        source.setUsername("root");
        source.setPassword("root");
        JdbcRealm realm = new JdbcRealm();
        realm.setDataSource(source);
        realm.setPermissionsLookupEnabled(true);
        DefaultSecurityManager manager = new DefaultSecurityManager();
        manager.setRealm(realm);
        SecurityUtils.setSecurityManager(manager);


        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("liming","liming");
        subject.login(token);
        System.out.println("登录结果："+subject.isAuthenticated());
        System.out.println("是否管理员："+subject.hasRole("root"));
        System.out.println("是否普通用户："+subject.hasRole("user"));
        System.out.println("用户名："+subject.getPrincipal());
        System.out.println("是否有删除权限："+subject.isPermitted("conm:delete"));
        System.out.println("是否有添加权限："+subject.isPermitted("conm:add"));
        subject.logout();
        System.out.println("登出后是否有权限："+subject.isAuthenticated());
    }


    /**
     * 自定义realm，构建SecurityManager环境
     */
    @Test
    public void customerRealmAuthTest(){
        CustomerRealm realm = new CustomerRealm();
        manager.setRealm(realm);
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("liming","root");
        subject.login(token);
        System.out.println("登录结果："+subject.isAuthenticated());
        System.out.println("是否管理员："+subject.hasRole("root"));
        System.out.println("是否普通用户："+subject.hasRole("user"));
        System.out.println("用户名："+subject.getPrincipal());
        System.out.println("是否有删除权限："+subject.isPermitted("conm:delete"));
        System.out.println("是否有修改权限："+subject.isPermitted("conm:update"));
        subject.logout();
        System.out.println("登出后是否有权限："+subject.isAuthenticated());
    }
}
