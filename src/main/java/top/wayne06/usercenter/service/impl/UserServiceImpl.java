package top.wayne06.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.wayne06.usercenter.mapper.UserMapper;
import top.wayne06.usercenter.model.domain.User;
import top.wayne06.usercenter.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值,将密码进行混淆
     */
    private static final String SALT = "tlas";

    public static final String USER_LOGIN_STATE = "USER_LOGIN_STATE";

    @Override
    public long userRegister(String userAccount, String password, String repeatPassword) {
        // 1.校验
        // 非空校验
        if (StringUtils.isAnyBlank(userAccount, password, repeatPassword)) {
            return -1;
        }
        // 账号长度不小于4位
        if (userAccount.length() < 4) {
            return -1;
        }
        // 密码不小于8位
        if (password.length() < 8 || repeatPassword.length() < 8) {
            return -1;
        }
        // 账户不包含特殊字符，特殊字符使用正则表达式筛选
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+ | {}【】‘；：”“’。，、？]";
        // 使用正则表达式进行校验
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        // 密码和校验密码是否相同
        if (!password.equals(repeatPassword)) {
            return -1;
        }
        // 账户不能重复，查询数据库当中是否存在相同名称用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        // 对密码进行加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 将数据插入数据库
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String password, HttpServletRequest request) {
        // 非空校验
        if (StringUtils.isAnyBlank(userAccount, password)) {
            return null;
        }
        // 账号长度不小于4位
        if (userAccount.length() < 4) {
            return null;
        }
        // 密码不小于8位
        if (password.length() < 8) {
            return null;
        }
        // 账户不包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        // 使用正则表达式进行校验
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }
        // 对密码进行加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
        }
        // 用户信息脱敏
        User safetyUser = getSafetyUser(user);
        // 用户登录成功
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    private User getSafetyUser(User user) {
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setUserCode(user.getUserCode());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        return safetyUser;
    }


}




