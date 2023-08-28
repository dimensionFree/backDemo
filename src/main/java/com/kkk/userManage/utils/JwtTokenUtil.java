package com.kkk.userManage.utils;

import com.kkk.config.exceptionHandler.ExceptionEnum;
import com.kkk.config.exceptionHandler.MyException;
import com.kkk.config.filter.AudienceConfig;
import com.kkk.config.filter.JwtInterceptor;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtil {
    static Logger log= LoggerFactory.getLogger(JwtTokenUtil.class);


    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 解析jwt
     *
     * @param jsonWebToken   需要解析的token
     * @param base64Security 加密信息
     * @return 解析后的JWT Claims
     */
    public static Claims parseJwt(String jsonWebToken, String base64Security) {
        try {
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
        } catch (ExpiredJwtException eje) {
            log.error("token expire : ", eje);
//            throw new BusinessException(MsgEnum.TOKEN_IS_EXPIRE.getCode(), MsgEnum.TOKEN_IS_EXPIRE.getMsg());
            throw new MyException(ExceptionEnum.TOKEN_EXPIRED,eje);
        } catch (Exception e) {
            log.error("token invalid :", e);
            throw new MyException(ExceptionEnum.TOKEN_INVALID,e);

        }
    }

    /**
     * 构建jwt
     *
     * @param userId   用户ID
     * @param username 用户名称
     * @param audience 加密配置信息
     * @return 创建好的JWT
     */
    public static String createJwt(Long userId, String username, AudienceConfig audience) {
        try {
            // 使用HS256加密算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            //生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(audience.getBase64Secret());
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            //添加构成JWT的参数
            JwtBuilder builder = Jwts.builder().setHeaderParam("type", "JWT")
                    // 可以将基本不重要的对象信息放到claims
                    .claim("userId", userId)
                    .setSubject(username)           // 代表这个JWT的主体，即它的所有人
                    .setIssuer(audience.getClientId())  // 代表这个JWT的签发主体；
                    .setIssuedAt(new Date())        // 是一个时间戳，代表这个JWT的签发时间；
                    .setAudience(audience.getName())   // 代表这个JWT的接收对象；
                    .signWith(signatureAlgorithm, signingKey);
            //添加Token过期时间
            int TTLMillis = audience.getExpiresSecond();
            if (TTLMillis >= 0) {
                long expMillis = nowMillis + TTLMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp)  // 是一个时间戳，代表这个JWT的过期时间；
                        .setNotBefore(now); // 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
            }
            //生成JWT
            return builder.compact();
        } catch (Exception e) {
            log.error("sign error : ", e);
            throw new MyException(ExceptionEnum.TOKEN_INVALID,e);

        }
    }

    /**
     * 从token中获取用户名
     *
     * @param token          token信息
     * @param base64Security 加密信息
     * @return 用户名称
     */
    public static String getUsername(String token, String base64Security) {
        return parseJwt(token, base64Security).getSubject();
    }

    /**
     * 从token中获取用户ID
     *
     * @param token          token信息
     * @param base64Security 加密信息
     * @return 用户ID
     */
    public static Long getUserId(String token, String base64Security) {
        return parseJwt(token, base64Security).get("userId", Long.class);
    }

    /**
     * 是否已过期
     *
     * @param token          token信息
     * @param base64Security 加密信息
     * @return token是否过期
     */
    public static boolean isExpiration(String token, String base64Security) {
        return parseJwt(token, base64Security).getExpiration().before(new Date());
    }
}
