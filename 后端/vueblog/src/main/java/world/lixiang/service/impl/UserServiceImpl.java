package world.lixiang.service.impl;

import org.springframework.stereotype.Service;
import world.lixiang.dao.UserDao;
import world.lixiang.entity.User;
import world.lixiang.service.UserService;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;

    @Override
    public User findUser(User user) {
        User user1 =  userDao.findUser(user);
        if(user1 == null){
            throw  new RuntimeException("用户名密码错误");
        }
        return user1;
    }
}
