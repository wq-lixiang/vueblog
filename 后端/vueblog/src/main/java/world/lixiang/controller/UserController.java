package world.lixiang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import world.lixiang.entity.User;
import world.lixiang.service.UserService;
import world.lixiang.vo.Result;

@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("findUser")
    public Result findUser(@RequestBody  User user){
        Result result = new Result();
        try{
            User user1 =  userService.findUser(user);
            result =  result.success("登录成功", user1);
        }catch (RuntimeException e){
            e.printStackTrace();
            result =  result.error("用户名密码错误");
        }
        return result;
    }
}
