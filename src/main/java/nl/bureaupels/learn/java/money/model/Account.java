package nl.bureaupels.learn.java.money.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iban4j.Iban;
import org.javamoney.moneta.Money;

@Data
@Builder(builderClassName = "Builder")
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Long id;
    private Iban iban;
    private Money balance;
}
