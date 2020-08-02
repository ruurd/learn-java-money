package nl.bureaupels.learn.java.money.mapping.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.javamoney.moneta.Money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class MoneyType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.CHAR, Types.DECIMAL};
    }

    @Override
    public Class returnedClass() {
        return Money.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        if (rs.wasNull())
            return null;

        String currency = rs.getString(names[0]);
        BigDecimal amount = rs.getBigDecimal(names[1]);
        return Money.of(amount, currency);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (Objects.isNull(value)) {
            st.setNull(index, Types.CHAR);
            st.setNull(index + 1, Types.DECIMAL);
        } else {
            Money money = (Money) value;
            st.setString(index, money.getCurrency().getCurrencyCode());
            st.setBigDecimal(index+1, money.getNumberStripped());
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        Money money = (Money)value;
        return Money.of(money.getNumber(), money.getCurrency());
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
