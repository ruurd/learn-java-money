package nl.bureaupels.learn.java.money.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class AccountListTest {
    @Test
    public void testConstruct() {
        assertThat("can construct", new AccountList(List.of(Account.builder().build())), not(nullValue()));
    }

}
