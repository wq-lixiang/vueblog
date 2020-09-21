package world.lixiang.service.impl;

import org.springframework.stereotype.Service;
import world.lixiang.dao.UserDao;
import world.lixiang.entity.User;
import world.lixiang.service.UserService;
import world.lixiang.utils.JwtInfo;
import world.lixiang.utils.JwtUtils;
import world.lixiang.utils.MD5;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;

    @Override
    public String findUser(User user) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        User user1 =  userDao.findUser(user);
        if(user1 == null){
            throw  new RuntimeException("用户名密码错误");
        }

        //登录：生成token
        JwtInfo info = new JwtInfo();
        info.setId(String.valueOf(user1.getId()));
        info.setNickname(user1.getUsername());

        String jwtToken = JwtUtils.getJwtToken(info, 1800);

        return jwtToken;
    }

    @Override
    public void addUser(User user) {
        String username = user.getUsername();
        User s = userDao.selectOneUser(username);
        if(s != null){
            throw new RuntimeException("用户名被注册");
        }
        user.setPassword(MD5.encrypt(user.getPassword()));
        userDao.insertUser(user);

    }
}
