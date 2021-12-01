package nl.bureaupels.learn.java.money.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.bureaupels.learn.java.money.annotations.Trackable;
import nl.bureaupels.learn.java.money.annotations.Tracked;
import org.iban4j.Iban;
import org.javamoney.moneta.Money;

@Trackable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Tracked
    private Long id;

    @Tracked
    private Iban iban;

    @Tracked
    private Money balance;
}
