package nl.bureaupels.learn.java.money.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.iban4j.Iban;
import org.javamoney.moneta.Money;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static nl.bureaupels.learn.java.money.entity.EntityConstants.TABLE_ACCOUNTS;

@Table(name = TABLE_ACCOUNTS)
@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_id_generator")
    @SequenceGenerator(name = "accounts_id_generator", sequenceName = "accounts_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "iban")
    @Type(type = "nl.bureaupels.learn.java.money.mapping.hibernate.IbanType")
    private Iban iban;

    @Columns(columns = {
        @Column(name = "currency"),
        @Column(name = "balance")
    })
    @Type(type = "nl.bureaupels.learn.java.money.mapping.hibernate.MoneyType")
    private Money balance;
}
