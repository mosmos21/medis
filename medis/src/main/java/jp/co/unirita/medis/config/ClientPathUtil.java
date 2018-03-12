package jp.co.unirita.medis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "medis.client")
public class ClientPathUtil {
    private String path;
}
