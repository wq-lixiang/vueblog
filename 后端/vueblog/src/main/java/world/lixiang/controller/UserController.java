package world.lixiang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import world.lixiang.entity.User;
import world.lixiang.service.UserService;
import world.lixiang.utils.JwtInfo;
import world.lixiang.utils.JwtUtils;
import world.lixiang.vo.Result;

import javax.servlet.http.HttpServletRequest;

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
            String token =  userService.findUser(user);
            result =  result.success("登录成功", token);
        }catch (RuntimeException e){
            e.printStackTrace();
            result =  result.error("用户名密码错误");
        }
        return result;
    }

    @PostMapping("register")
    public Result register(@RequestBody User user){
        Result result = new Result();
        try {
            userService.addUser(user);
            result = result.success("注册成功");
        }catch (Exception e){
            e.printStackTrace();
            result = result.error("用户名被注册");
        }

        return result;
    }

    @GetMapping("get-login-info")
    public Result getLoginInfo(HttpServletRequest request){
        Result result = new Result();
        try {
            JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
            result = result.success("获取成功",jwtInfo);
        } catch (Exception e) {
            e.printStackTrace();
            result = result.error("登录过长重新登录");
        }
        return result;
    }
}
