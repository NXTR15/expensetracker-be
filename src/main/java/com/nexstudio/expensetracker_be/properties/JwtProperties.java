package com.nexstudio.expensetracker_be.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "extracker.jwt")
public class JwtProperties {
    private String secret;
    private Long expiration;
    private Long refreshExpiration;
    private String issuer;
}
