package nl.bureaupels.learn.java.money.mapping.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.iban4j.Iban;

import java.io.IOException;

public class IbanSerializer extends StdSerializer<Iban> {

    public IbanSerializer() {
        this(Iban.class);
    }

    public IbanSerializer(Class<Iban> t) {
        super(t);
    }

    @Override
    public void serialize(Iban value, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
        jsonGenerator.writeString(value.toString());
    }
}
