package org.example.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mail")
@Getter
@Setter
public class MailProperties {
    private String from;
    private String sender;
    private String subject;
    private String content;
    private String baseUrl;
}
