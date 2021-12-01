package nl.bureaupels.learn.java.money.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.ToString;
import nl.bureaupels.learn.java.money.annotations.Countable;
import nl.bureaupels.learn.java.money.annotations.Trackable;

import java.util.List;

@Trackable
@Getter
@Builder
@ToString
@RequiredArgsConstructor
public class AccountList {

    @Countable
    @Singular
    private final List<Account> accounts;
}
