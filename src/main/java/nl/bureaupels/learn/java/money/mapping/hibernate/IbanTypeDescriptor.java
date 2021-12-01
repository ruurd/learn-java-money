package nl.bureaupels.learn.java.money.mapping.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.iban4j.Iban;
import org.iban4j.IbanFormat;

public class IbanTypeDescriptor extends AbstractTypeDescriptor<Iban> {

    public static final IbanTypeDescriptor INSTANCE = new IbanTypeDescriptor(Iban.class, ImmutableMutabilityPlan.INSTANCE);

    protected IbanTypeDescriptor(Class<Iban> type) {
        super(type);
    }

    protected IbanTypeDescriptor(Class<Iban> type, MutabilityPlan<Iban> mutabilityPlan) {
        super(type, mutabilityPlan);
    }

    @Override
    public String toString(Iban value) {
        return value.toString();
    }

    @Override
    public Iban fromString(String string) {
        return Iban.valueOf(string);
    }

    @Override
    public <X> X unwrap(Iban value, Class<X> type, WrapperOptions options) throws HibernateException {
        X result = null;
        if (value != null) {
            if (type.isAssignableFrom(String.class)) {
                result = (X) value.toString();
            } else {
                unknownUnwrap(type);
            }
        }
        return result;
    }

    @Override
    public <X> Iban wrap(X value, WrapperOptions options) throws HibernateException {
        Iban result = null;
        if (value != null) {
            if (value instanceof String) {
                result = Iban.valueOf((String) value, IbanFormat.None);
            } else {
                unknownWrap(value.getClass());
            }
        }
        return result;
    }
}
