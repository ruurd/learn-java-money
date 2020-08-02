package nl.bureaupels.learn.java.money.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class AccountList {

    @Singular
    private final List<Account> accounts;
}
