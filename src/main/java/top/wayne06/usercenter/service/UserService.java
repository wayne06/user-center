package top.wayne06.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.wayne06.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public interface UserService extends IService<User> {

    long userRegister(String userAccount, String password, String repeatPassword);

    User userLogin(String userAccount, String password, HttpServletRequest request);

    User getSecureUser(User originalUser);
}
