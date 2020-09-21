package world.lixiang.dao;

import world.lixiang.entity.User;

public interface UserDao {
    User findUser(User user);

    void insertUser(User user);

    User selectOneUser(String username);
}
