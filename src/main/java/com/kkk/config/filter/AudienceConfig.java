package com.kkk.config.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "audience")
@Configuration("audience")
public class AudienceConfig {
    private String clientId;
    private String base64Secret;
    private String name;
    private int expiresSecond;
}
