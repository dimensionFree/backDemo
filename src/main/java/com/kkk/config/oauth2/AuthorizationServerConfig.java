package com.kkk.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 授权服务器
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    public static final String CLIENT_ID = "client_local";
    // 该对象用来支持 password 模式
    @Autowired
    AuthenticationManager authenticationManager;

//    // 该对象用来将令牌信息存储到内存中
//    @Autowired(required = false)
//    TokenStore inMemoryTokenStore;

    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    // 该对象将为刷新token提供支持
    @Autowired
    UserDetailsService userDetailsService;

    // 配置 password 授权模式
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {

        clients.inMemory()
                //授权Id
                .withClient(CLIENT_ID)
                .authorizedGrantTypes("password", "refresh_token") //授权模式为password和refresh_token两种
                .accessTokenValiditySeconds(1800) // 配置access_token的过期时间
//                .resourceIds("rid") //配置资源id
                .scopes("all")
                //授权密码
                .secret("$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq"); //123加密后的密码
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                //accessToken -> jwt
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter);
//        .tokenStore(inMemoryTokenStore) //配置令牌的存储（这里存放在内存中）
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        // 表示支持 client_id 和 client_secret 做登录认证
        security.allowFormAuthenticationForClients();
    }
}