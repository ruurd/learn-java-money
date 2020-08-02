package nl.bureaupels.learn.java.money.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("app.config.api")
@RefreshScope
@Component
public class MoneyApiProperties {
    private final String urlPrefix = "/api/money";
}
