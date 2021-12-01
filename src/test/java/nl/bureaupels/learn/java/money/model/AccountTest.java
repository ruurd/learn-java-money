package nl.bureaupels.learn.java.money.model;

import org.iban4j.Iban;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class AccountTest {
    private Account account;

    @BeforeEach
    public void beforeEach() {
        account = new Account(1L, Iban.random(), Money.of(123L, "EUR"));
    }

    @Test
    public void testConstruct() {
        assertThat("can construct", account, not(nullValue()));
    }

    @Test
    public void testChangeBalance() {
        account.setBalance(Money.of(321L, "USD"));
        assertThat("balance in USD", account.getBalance().getCurrency().getCurrencyCode(), is("USD"));
    }

    @Test
    public void testChangeIban() {
        Iban randiban = Iban.random();
        account.setIban(randiban);
        assertThat("different iban", account.getIban(), is(randiban));
    }

}
