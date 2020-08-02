package nl.bureaupels.learn.java.money.service;

import lombok.RequiredArgsConstructor;
import nl.bureaupels.learn.java.money.entity.AccountEntity;
import nl.bureaupels.learn.java.money.model.Account;
import nl.bureaupels.learn.java.money.model.AccountList;
import nl.bureaupels.learn.java.money.repository.AccountRepository;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@RequiredArgsConstructor
@EnableTransactionManagement
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public AccountList allAccounts() {
        AccountList.Builder resultBuilder = AccountList.builder();
        accountRepository.findAll().forEach(accountEntity -> resultBuilder.account(createAccountfromEntity(accountEntity)));
        return resultBuilder.build();
    }

    public Optional<Account> findAccountById(Long id) {
        Optional<Account> result = Optional.empty();
        AccountEntity accountEntity = accountRepository.getAccountEntityById(id);
        if (accountEntity != null) {
            result = Optional.of(createAccountfromEntity(accountEntity));
        }
        return result;
    }

    public Optional<Account> findAccountByIban(Iban iban) {
        Optional<Account> result = Optional.empty();
        AccountEntity accountEntity = accountRepository.getAccountEntityByIban(iban);
        if (accountEntity != null) {
            result = Optional.of(createAccountfromEntity(accountEntity));
        }
        return result;
    }

    public Account addAccount(Account newAccount) {
        AccountEntity newAccountEntity = createEntityFromAccount(newAccount, true);
        return createAccountfromEntity(accountRepository.save(newAccountEntity));
    }

    public Account updateAccount(Account existingAccount) {
        AccountEntity existingAccountEntity = createEntityFromAccount(existingAccount, false);
        return createAccountfromEntity(accountRepository.save(existingAccountEntity));
    }

    public void deleteAccount(Account existingAccount) {
        AccountEntity existingAccountEntity = createEntityFromAccount(existingAccount, false);
        accountRepository.delete(existingAccountEntity);
    }

    private AccountEntity createEntityFromAccount(Account account, boolean newAccount) {
        AccountEntity entity = new AccountEntity();
        if (newAccount) {
            entity.setId(null);
        } else {
            entity.setId(account.getId());
        }
        entity.setIban(account.getIban());
        entity.setBalance(account.getBalance());
        return entity;
    }

    private Account createAccountfromEntity(AccountEntity entity) {
        return Account.builder()
            .id(entity.getId())
            .iban(entity.getIban())
            .balance(entity.getBalance())
            .build();
    }
}
