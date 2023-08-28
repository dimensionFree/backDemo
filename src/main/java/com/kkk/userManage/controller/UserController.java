package com.kkk.userManage.controller;

import com.kkk.config.filter.LoginUserContext;
import com.kkk.userManage.entity.User;
import com.kkk.userManage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@CrossOrigin(origins="*",maxAge=3600)
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public User addUser(@RequestBody @Valid User user){

        return userService.saveUser(user);
    }

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable Long id, @RequestHeader Map<String,String> header){
//        LoginUserContext.getUser();
        return userService.findUserById(id);
    }

    @GetMapping("/user")
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }




}
