package com.kkk.config.filter;

import com.kkk.config.exceptionHandler.ExceptionEnum;
import com.kkk.config.exceptionHandler.MyException;
import com.kkk.userManage.entity.User;
import com.kkk.userManage.utils.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    Logger log= LoggerFactory.getLogger(JwtInterceptor.class);
    private final AudienceConfig audience;

    @Autowired
    public JwtInterceptor(AudienceConfig audience) {
        this.audience = audience;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 忽略带JwtIgnore注解的请求, 不做后续token认证校验
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            JwtIgnore jwtIgnore = handlerMethod.getMethodAnnotation(JwtIgnore.class);
            if (jwtIgnore != null) {
                return true;
            }
        }
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        // 获取请求头信息authorization信息
        final String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
//            throw new MyException(ExceptionEnum.TOKEN_INVALID);
            return true;
        }
        // 获取token
        final String token = authHeader.substring(7);

        // 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
        JwtTokenUtil.parseJwt(token, audience.getBase64Secret());

        // token验证完成后获取对应信息，存放在本地线程中
        User user = new User();
        String username = JwtTokenUtil.getUsername(token, this.audience.getBase64Secret());
        Long userId = JwtTokenUtil.getUserId(token, this.audience.getBase64Secret());
        user.setId(userId);
        user.setUsername(username);
        LoginUserContext.setLoginUser(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("remove before ---------------{}", LoginUserContext.getUser());
        LoginUserContext.remove();
        log.info("remove after ---------------{}", LoginUserContext.getUser());
    }
}
