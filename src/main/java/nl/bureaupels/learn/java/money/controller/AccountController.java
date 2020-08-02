package nl.bureaupels.learn.java.money.controller;

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
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = ControllerConstants.URL_PREFIX)
public class AccountController {

    private final AccountService accountService;
    private Account existingAccount;
    private Long id;

    @GetMapping(value = "/accounts", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AccountList> getAllAccounts() {
        try {
            return ResponseEntity.ok(accountService.allAccounts());
        } catch (Exception x) {
            log.error("wut? {}", x);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/accounts/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Account> getAccountById(@PathVariable(name = "id") @Min(value = 0) Long id) {
        ResponseEntity<Account> result;
        Optional<Account> account = accountService.findAccountById(id);
        return account.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/accounts", params = {"iban"}, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Account> getAccountByIban(@RequestParam(name = "iban") @Min(value = 18) @Max(value = 34) String iban) {
        ResponseEntity<Account> result;
        Optional<Account> account = accountService.findAccountByIban(Iban.valueOf(iban));
        return account.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/accounts", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Account> createAccount(@RequestBody Account newAccount) {
        try {
            return ResponseEntity.ok(accountService.addAccount(newAccount));
        } catch (DataIntegrityViolationException divx) {
            log.info("cannot add account: {}", divx);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/accounts/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Account> updateAccount(@RequestBody Account existingAccount, @PathVariable(name = "id") @Min(value = 0) Long id) {
        Optional<Account> account = accountService.findAccountById(id);
        if (account.isPresent()) {
            return ResponseEntity.ok(accountService.updateAccount(existingAccount));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/accounts/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> deleteAccount(@PathVariable(name = "id") @Min(value = 0) Long id) {
        Optional<Account> account = accountService.findAccountById(id);
        if (account.isPresent()) {
            accountService.deleteAccount(account.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
