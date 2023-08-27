package com.kkk.userManage.controller;

import com.kkk.userManage.entity.User;
import com.kkk.userManage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public User findUserById(@PathVariable Long id){
        return userService.findUserById(id);
    }

    @GetMapping("/user")
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }




}
