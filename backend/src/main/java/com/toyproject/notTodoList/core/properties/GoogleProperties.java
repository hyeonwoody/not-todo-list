package com.toyproject.notTodoList.core.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "google")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoogleProperties {
    @Setter
    private String header;
    @Setter
    private String issuer;
    @Setter
    private String clientSecret;
    @Setter
    private String algorithm;
    @Setter
    private int accessTokenExpiryHour;
    @Setter
    private int refreshTokenExpiryHour;
}
