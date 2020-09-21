package world.lixiang.service;

import world.lixiang.entity.User;

public interface UserService {
    String findUser(User user);

    void addUser(User user);
}
