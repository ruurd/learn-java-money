package nl.bureaupels.learn.java.money.mapping.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.AmountFormatParams;
import org.javamoney.moneta.format.CurrencyStyle;

import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.io.IOException;
import java.util.Locale;

public class MoneySerializer extends StdSerializer<Money> {

    private static final MonetaryAmountFormat MONETARY_AMOUNT_FORMAT = MonetaryFormats.getAmountFormat(
        AmountFormatQueryBuilder.of(Locale.ROOT)
            .set(CurrencyStyle.CODE)
            .set(AmountFormatParams.GROUPING_SIZES, new int[]{2, 0})
            .build()
    );


    public MoneySerializer() {
        this(Money.class);
    }

    public MoneySerializer(Class<Money> t) {
        super(t);
    }

    @Override
    public void serialize(Money value, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
        jsonGenerator.writeString(MONETARY_AMOUNT_FORMAT.format(value));
    }
}
