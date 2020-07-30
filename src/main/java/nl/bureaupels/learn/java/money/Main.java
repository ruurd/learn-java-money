package nl.bureaupels.learn.java.money;

import org.javamoney.moneta.Money;

import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        CurrencyConversion conv = MonetaryConversions.getConversion("EUR", "ECB");
        Money pegels = Money.of(BigDecimal.valueOf(100L), "EUR");
        Money bucks = Money.of(BigDecimal.valueOf(200L), "USD");
        System.out.println(pegels);
        System.out.println(bucks);

        pegels = pegels.add(conv.apply(bucks));
        System.out.println(pegels);
    }
}
