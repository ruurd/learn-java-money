package nl.bureaupels.learn.java.money.trials;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.ToStringMonetaryAmountFormat;
import org.javamoney.moneta.format.AmountFormatParams;

import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;
import java.util.Locale;

import static org.javamoney.moneta.ToStringMonetaryAmountFormat.ToStringMonetaryAmountFormatStyle.MONEY;

// Print out a number using the localized number, integer, currency,
// and percent format for each locale</strong>
public class DumpMoneyFormat {
    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("nl_NL"));
        ToStringMonetaryAmountFormat madf = ToStringMonetaryAmountFormat.of(MONEY);
        System.out.println(madf.format(Money.of(BigDecimal.valueOf(1234.6789), "EUR")));

        MonetaryAmountFormat formatter = MonetaryFormats.getAmountFormat(
            AmountFormatQueryBuilder.of(Locale.ROOT)
                .set(AmountFormatParams.GROUPING_SIZES, new int[]{2, 0})
                //                .set(AmountFormatParams.PATTERN, "¤ 0.000000")
                .build()
        );
        System.out.println(formatter.format(Money.of(BigDecimal.valueOf(1234.6789), "EUR")));
    }
}
