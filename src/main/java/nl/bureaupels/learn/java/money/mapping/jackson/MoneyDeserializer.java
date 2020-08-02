package nl.bureaupels.learn.java.money.mapping.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.AmountFormatParams;
import org.javamoney.moneta.format.CurrencyStyle;

import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.io.IOException;
import java.util.Locale;

public class MoneyDeserializer extends StdDeserializer<Money> {

    private static final MonetaryAmountFormat MONETARY_AMOUNT_FORMAT = MonetaryFormats.getAmountFormat(
        AmountFormatQueryBuilder.of(Locale.ROOT)
            .set(CurrencyStyle.CODE)
            .set(AmountFormatParams.GROUPING_SIZES, new int[]{2, 0})
            .build()
    );

    public MoneyDeserializer() {
        this(null);
    }

    public MoneyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Money deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        Money result = null;
        if (node != null) {
            MonetaryAmount ma = MONETARY_AMOUNT_FORMAT.parse(node.textValue());
            result = Money.of(ma.getNumber(), ma.getCurrency());
        }
        return result;
    }
}
