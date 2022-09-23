package com.kkk.userManage.controller;

import com.auth0.jwt.interfaces.Claim;
import com.kkk.userManage.entity.User;
import com.kkk.userManage.utils.JwtTest;
import com.kkk.userManage.utils.JwtTokenUtils;
import com.kkk.userManage.utils.jwtPojo.JwtInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping
    public String login(@RequestBody User user) throws Exception {
        System.out.println(user.toString());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);

        System.out.println(authenticate.isAuthenticated());

        return authenticate.toString();

    }




}
