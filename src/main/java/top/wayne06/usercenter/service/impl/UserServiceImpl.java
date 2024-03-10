package top.wayne06.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.wayne06.usercenter.model.domain.User;
import top.wayne06.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;
import top.wayne06.usercenter.service.UserService;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}




