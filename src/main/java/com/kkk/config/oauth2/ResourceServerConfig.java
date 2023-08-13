package com.kkk.config.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务器
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//
//        resources.resourceId("rid") // 配置资源id，这里的资源id和授权服务器中的资源id一致
//                .stateless(true); // 设置这些资源仅基于令牌认证
//    }

    // 配置 URL 访问权限
    /*
    通过 authorizeRequests() 定义哪些URL需要被保护、哪些不需要被保护
    通过 formLogin() 定义当需要用户登录时候，转到的登录页面。
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
//                .antMatchers("/hello").hasAuthority("user")
//                .hasAuthority(Role.user.name())
                .mvcMatchers(HttpMethod.POST,"/user").anonymous()
                .mvcMatchers(HttpMethod.POST,"/login").permitAll()
                .mvcMatchers(HttpMethod.GET,"/user").permitAll()
                .mvcMatchers(HttpMethod.GET,"/user/*").hasRole("user")
                .anyRequest().authenticated()

                .and()
                .cors()// 启用CORS支持
//                .and()
                //指定登录认证的Controller
//                .formLogin().permitAll()
//                .and()
//                .logout().permitAll()
                .and().httpBasic();

        //        https://blog.csdn.net/qq_21602341/article/details/114577740
        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.authorizeRequests()
//                .antMatchers("/admin/**").hasRole("admin")
//                .antMatchers("/user/**").hasRole("user")
//                .antMatchers("/user/**").hasRole("user")
//
//                .anyRequest().authenticated();
    }
}
