package nl.bureaupels.learn.java.money.repository;

import nl.bureaupels.learn.java.money.entity.AccountEntity;
import org.iban4j.Iban;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

    AccountEntity getAccountEntityById(Long id);

    AccountEntity getAccountEntityByIban(Iban iban);

}
