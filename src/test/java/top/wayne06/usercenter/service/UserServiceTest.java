package top.wayne06.usercenter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.wayne06.usercenter.model.domain.User;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("aaa");
        user.setUserAccount("123");
        user.setAvatarUrl("https://wx2.sinaimg.cn/orj480/5c527595ly8gbso2z640bj208d06uq2p.jpg");
        user.setGender((byte)0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

}
