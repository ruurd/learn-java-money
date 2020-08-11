package nl.bureaupels.learn.java.money;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoneyApplication {

    public static final String APPLICATION = "Money";

    public static void main(String[] args) {
        SpringApplication.run(MoneyApplication.class, args);
    }
}
