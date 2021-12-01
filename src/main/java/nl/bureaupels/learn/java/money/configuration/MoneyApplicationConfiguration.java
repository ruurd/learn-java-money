package nl.bureaupels.learn.java.money.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.swagger.v3.core.jackson.ModelResolver;
import nl.bureaupels.learn.java.money.mapping.jackson.IbanDeserializer;
import nl.bureaupels.learn.java.money.mapping.jackson.IbanSerializer;
import nl.bureaupels.learn.java.money.mapping.jackson.MoneyDeserializer;
import nl.bureaupels.learn.java.money.mapping.jackson.MoneySerializer;
import org.iban4j.Iban;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MoneyApplicationConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(new IbanSerializer());
        module.addDeserializer(Iban.class, new IbanDeserializer());
        module.addSerializer(new MoneySerializer());
        module.addDeserializer(Money.class, new MoneyDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper);
    }

}
