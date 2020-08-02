package nl.bureaupels.learn.java.money.mapping.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.iban4j.Iban;

import java.io.IOException;

public class IbanDeserializer extends StdDeserializer<Iban> {

    public IbanDeserializer() {
        this(null);
    }

    public IbanDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Iban deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String text = node.textValue();
        return Iban.valueOf(text);
    }
}
