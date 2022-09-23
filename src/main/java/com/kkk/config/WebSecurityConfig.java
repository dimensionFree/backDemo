package com.kkk.config;

import com.kkk.userManage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration//指定为配置类
@EnableWebSecurity//指定为Spring Security配置类
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    UserDetailsService Service(){
        return new UserService();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(Service()).passwordEncoder(passwordEncoder);
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password("$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq") //123
//                .roles("admin")
//                .and()
//                .withUser("sang")
//                .password("$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq") //123
//                .roles("user");
    }

    /*
    通过 authorizeRequests() 定义哪些URL需要被保护、哪些不需要被保护
    通过 formLogin() 定义当需要用户登录时候，转到的登录页面。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
//                .antMatchers("/hello").hasAuthority("user")
//                .hasAuthority(Role.user.name())
                .mvcMatchers(HttpMethod.POST,"/user").anonymous()
                .mvcMatchers(HttpMethod.POST,"/login").permitAll()
                .mvcMatchers(HttpMethod.GET,"/user").permitAll()
                .mvcMatchers(HttpMethod.GET,"/token/*").permitAll()
                .mvcMatchers(HttpMethod.GET,"/user/*").hasRole("user")
//
                .anyRequest().authenticated()

                .and()
                .cors()// 启用CORS支持
//                .and()
                //指定登录认证的Controller
//                .formLogin().permitAll()
//                .and()
//                .logout().permitAll()
                .and().httpBasic();
//        http.antMatcher("/oauth/**").authorizeRequests()
//                .antMatchers("/oauth/**").permitAll()
//                .and().csrf().disable();

//        https://blog.csdn.net/qq_21602341/article/details/114577740
        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }


    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许从对应站点跨域
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        // 允许使用GET方法和POST方法
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        // 允许带凭证
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //对所有URL生效
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}