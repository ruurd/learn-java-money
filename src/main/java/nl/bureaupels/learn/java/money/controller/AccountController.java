package nl.bureaupels.learn.java.money.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.bureaupels.learn.java.money.model.Account;
import nl.bureaupels.learn.java.money.model.AccountList;
import nl.bureaupels.learn.java.money.service.AccountService;
import org.iban4j.Iban;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
@Controller
@Validated
@RequestMapping(value = ControllerConstants.URL_PREFIX)
public class AccountController {

    private final AccountService accountService;
    private Account existingAccount;
    private Long id;

    @GetMapping(value = "/accounts", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AccountList> getAllAccounts() {
        try {
            log.info("getAllAcounts");
            return ResponseEntity.ok(accountService.allAccounts());
        } catch (Exception x) {
            log.error("cannot retrieve list of accounts", x);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/accounts/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Account> getAccountById(@PathVariable(name = "id") @Min(value = 0) Long id) {
        log.info("getAcountById({})", id);
        Optional<Account> account = accountService.findAccountById(id);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/accounts/iban/{iban}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Account> getAccountByIban(@PathVariable(name = "iban") String iban) {
        log.info("getAcountByIban({})", iban);
        Optional<Account> account = accountService.findAccountByIban(Iban.valueOf(iban));
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/accounts", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> createAccount(@Valid @RequestBody Account newAccount) {
        log.info("createAccount({})", newAccount);
        ResponseEntity<Void> result;
        try {
            Account createdAccount = accountService.addAccount(newAccount);
            result = ResponseEntity.created(URI.create("/" + createdAccount.getId().toString())).build();
        } catch (DataIntegrityViolationException divx) {
            log.info("cannot add account", divx);
            result = ResponseEntity.badRequest().build();
        }
        return result;
    }

    @PutMapping(value = "/accounts/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> updateAccount(@RequestBody Account existingAccount, @PathVariable(name = "id") @Min(value = 0) Long id) {
        log.info("updateAccount({})", id);
        Optional<Account> account = accountService.findAccountById(id);
        if (account.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/accounts/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> deleteAccount(@PathVariable(name = "id") @Min(value = 0) Long id) {
        log.info("deleteAccount({})", id);
        Optional<Account> account = accountService.findAccountById(id);
        if (account.isPresent()) {
            accountService.deleteAccount(account.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
