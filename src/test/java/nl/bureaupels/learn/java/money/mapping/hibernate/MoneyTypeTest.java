package nl.bureaupels.learn.java.money.mapping.hibernate;

import org.javamoney.moneta.Money;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class MoneyTypeTest {
    @Test
    public void testComparison() {
        Money left = Money.of(BigDecimal.valueOf(100L), "EUR");
        Money rite = Money.of(BigDecimal.valueOf(100L), "EUR");

        assertThat("are equal", left, is(rite));
        assertThat("but not same object", left == rite, is(false));

        MoneyType mtype = new MoneyType();
        assertThat("moneytype agrees", mtype.equals(left, rite), is(true));
    }

    @Test
    public void testDisassembleAssemble() {
        Money money = Money.of(BigDecimal.valueOf(100L), "EUR");
        MoneyType mtype = new MoneyType();
        Serializable ser = mtype.disassemble(money);
        assertThat("can disassemble", ser, not(nullValue()));

        Money conv = (Money) mtype.assemble(ser, null);
        assertThat("can assemble", conv, not(nullValue()));

        assertThat("are equal", conv, is(money));
    }

    @Test
    public void testNullSafeGet() throws SQLException {
        MoneyType mtype = new MoneyType();
        ResultSet resultSet = mock(ResultSet.class);
        String[] colums = {"currency", "amount"};
        when(resultSet.getBigDecimal("amount")).thenReturn(BigDecimal.valueOf(123L));
        when(resultSet.getString("currency")).thenReturn("EUR");
        when(resultSet.wasNull()).thenReturn(false);

        Object o = mtype.nullSafeGet(resultSet, colums, null, null);

        assertThat("can get", o, not(nullValue()));
        assertThat("is money", o, is(instanceOf(Money.class)));
        assertThat("is 123 EUR", o, is(Money.of(BigDecimal.valueOf(123L), "EUR")));
    }

    @Test
    public void testNullSafeSet() throws SQLException {
        Money money = Money.of(BigDecimal.valueOf(321L), "EUR");

        MoneyType mtype = new MoneyType();
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        ArgumentCaptor<BigDecimal> amountCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        ArgumentCaptor<Integer> amountIndexCaptor = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(preparedStatement).setBigDecimal(amountIndexCaptor.capture(), amountCaptor.capture());

        ArgumentCaptor<String> currencyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> currencyIndexCaptor = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(preparedStatement).setString(currencyIndexCaptor.capture(), currencyCaptor.capture());

        mtype.nullSafeSet(preparedStatement, money, 5, null);

        assertThat("currency ok", currencyCaptor.getValue(), is("EUR"));
        assertThat("amount ok", amountCaptor.getValue(), is(BigDecimal.valueOf(321L)));
        assertThat("indexes ok", currencyIndexCaptor.getValue() - amountIndexCaptor.getValue(), is(-1));
    }
}
