//package com.kkk.userManage.utils;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwt;
//import io.jsonwebtoken.JwtBuilder;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.impl.Base64Codec;
//import io.netty.handler.codec.base64.Base64Encoder;
//import junit.framework.TestCase;
//
//import org.apache.commons.lang3.time.DateUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Date;
//
//@SpringBootTest
//public class JwtUtilsTest {
//
//    @Test
//    public void get_jwt_should_work(){
//        JwtBuilder jwtBuilder = JwtUtils.getJwtBuilder()
//                .setId("id")
//                .setSubject("tony")
//                .setIssuedAt(new Date())
//                .signWith(SignatureAlgorithm.HS256, JwtUtils.JWT_SALT);
//        String token = jwtBuilder.compact();
//
//        System.out.println(token);
//        System.out.println("-------------");
//
//        for (String s : token.split("\\.")) {
//            System.out.println(Base64Codec.BASE64.decodeToString(s)) ;
//        }
//
//
//    }
//    @Test
//    public void parse_jwt_should_work(){
//        String jwt="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJpZCIsInN1YiI6InRvbnkiLCJpYXQiOjE2OTMyMDc3NDl9.FSvexiy1xKmcNQGOlNinrrU0iFhyyMS2k_j_m7BAXb4";
//        Jwt parse = JwtUtils.getJwtParser()
//                .setSigningKey(JwtUtils.JWT_SALT)
//                .parse(jwt);
//        Claims body = (Claims) parse
//                .getBody();
//
//
//        System.out.println(body);
//        System.out.println(parse.getHeader());
//        System.out.println("-------------");
//
//    }
//
//
//    @Test
//    public void set_jwt_expire_should_work(){
//        Date date = DateUtils.addSeconds(new Date(), 5);
//        JwtBuilder jwtBuilder = JwtUtils.getJwtBuilder()
//                .setId("id")
//                .setSubject("tony")
//                .setIssuedAt(new Date())
//                .setExpiration(date)
//                .signWith(SignatureAlgorithm.HS256, JwtUtils.JWT_SALT)
//                .claim("sex","male");
//        String token = jwtBuilder.compact();
//
//        System.out.println(token);
//        System.out.println("-------------");
//
//        for (String s : token.split("\\.")) {
//            System.out.println(Base64Codec.BASE64.decodeToString(s)) ;
//        }
//
//
//    }
//
//
//}