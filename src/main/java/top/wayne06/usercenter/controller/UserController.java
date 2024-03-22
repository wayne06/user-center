package top.wayne06.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.wayne06.usercenter.common.GeneralResponse;
import top.wayne06.usercenter.common.ResultCode;
import top.wayne06.usercenter.exception.BusinessException;
import top.wayne06.usercenter.model.domain.User;
import top.wayne06.usercenter.model.request.UserLoginRequest;
import top.wayne06.usercenter.model.request.UserRegisterRequest;
import top.wayne06.usercenter.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static top.wayne06.usercenter.constant.UserConstant.ADMIN_ROLE;
import static top.wayne06.usercenter.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public GeneralResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            //return GeneralResponse.failure(ResultCode.PARAMS_ERROR);
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getUserPassword();
        String repeatPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, password, repeatPassword)) {
            return null;
        }
        //return userService.userRegister(userAccount, password, repeatPassword);
        Long id = userService.userRegister(userAccount, password, repeatPassword);
        return GeneralResponse.success(id);
    }

    @PostMapping("/login")
    public GeneralResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        //return userService.userLogin(userAccount, userPassword, request);
        User user = userService.userLogin(userAccount, userPassword, request);
        return GeneralResponse.success(user);
    }

    @GetMapping("/search")
    public GeneralResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        // 管理员校验
        if (!isAdmin(request)) {
            return GeneralResponse.failure(ResultCode.REDIRECT);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> secureUsers = userList.stream().map(userService::getSecureUser).collect(Collectors.toList());
        return GeneralResponse.success(secureUsers);
    }

    @PostMapping("/delete")
    public GeneralResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return GeneralResponse.success(false);
        }
        if (id < 0) {
            return GeneralResponse.success(false);
        }
        Boolean result = userService.removeById(id);
        return GeneralResponse.success(result);
    }

    private boolean isAdmin(HttpServletRequest request) {
        // 管理员校验
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user == null || user.getUserRole() != ADMIN_ROLE) {
            return false;
        }
        return true;
    }

}
