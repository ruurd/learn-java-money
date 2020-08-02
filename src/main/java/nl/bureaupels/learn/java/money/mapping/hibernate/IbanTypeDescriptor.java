package nl.bureaupels.learn.java.money.mapping.hibernate;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.iban4j.Iban;
import org.iban4j.IbanFormat;

import java.util.Comparator;

public class IbanTypeDescriptor implements JavaTypeDescriptor<Iban> {

    public static final IbanTypeDescriptor INSTANCE = new IbanTypeDescriptor();
    public static final MutabilityPlan<Iban> MUTABILITY_PLAN = new ImmutableMutabilityPlan<>();

    @Deprecated
    @Override
    public Class<Iban> getJavaTypeClass() {
        return getJavaType();
    }

    @Override
    public Class<Iban> getJavaType() {
        return Iban.class;
    }

    @Override
    public MutabilityPlan<Iban> getMutabilityPlan() {
        return MUTABILITY_PLAN;
    }

    Comparator<Iban> ibanComparator = Comparator.comparing(Iban::toString);

    @Override
    public Comparator<Iban> getComparator() {
        return ibanComparator;
    }

    @Override
    public int extractHashCode(Iban value) {
        return value.hashCode();
    }

    @Override
    public boolean areEqual(Iban one, Iban another) {
        return one.equals(another);
    }

    @Override
    public String extractLoggableRepresentation(Iban value) {
        return value.toString();
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
    public <X> X unwrap(Iban value, Class<X> type, WrapperOptions options) {
        return (X) value.toString();
    }

    @Override
    public <X> Iban wrap(X value, WrapperOptions options) {
        if (value == null)
            return null;
        return Iban.valueOf((String) value, IbanFormat.None);
    }
}
