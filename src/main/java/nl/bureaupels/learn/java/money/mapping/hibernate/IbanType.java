package nl.bureaupels.learn.java.money.mapping.hibernate;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.CharTypeDescriptor;
import org.iban4j.Iban;

public class IbanType extends AbstractSingleColumnStandardBasicType<Iban> {

    public static final IbanType INSTANCE = new IbanType();

    public IbanType() {
        super(CharTypeDescriptor.INSTANCE, IbanTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "Iban";
    }
}
