package com.kkk.userManage.controller;

import com.auth0.jwt.interfaces.Claim;
import com.kkk.userManage.utils.JwtTest;
import com.kkk.userManage.utils.JwtTokenUtils;
import com.kkk.userManage.utils.jwtPojo.JwtInfo;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("token")
public class TokenController {


    @GetMapping("/create")
    public String createToken(@RequestBody Map map) throws Exception {
        Long uid = Long.valueOf (map.get("uid").toString());
        return JwtTest.createToken(uid);
    }


    @GetMapping("/verify")
    public Map<String, Claim> verifyToken(@RequestBody Map map) throws Exception {
        String token = (String) map.get("token");
        return JwtTest.verifyToken(token);
    }

    @GetMapping("/createByUtil")
    public String createTokenByUtil(@RequestBody JwtInfo jwtInfo) throws Exception {
        return JwtTokenUtils.generatorToken(jwtInfo,10);
    }


    @GetMapping("/verifyByUtil")
    public JwtInfo verifyTokenByUtil(@RequestBody Map map) throws Exception {
        String token = (String) map.get("token");
        return JwtTokenUtils.getTokenInfo(token);
    }

}
