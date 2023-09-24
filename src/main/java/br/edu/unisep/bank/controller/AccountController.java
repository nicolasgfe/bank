package br.edu.unisep.bank.controller;

import br.edu.unisep.bank.exception.ResourceNotFoundException;
import br.edu.unisep.bank.model.Account;
import br.edu.unisep.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    @Autowired
    private AccountRepository repository;

    @GetMapping("/conta")
    public List<Account> getAllAccounts(){
        return repository.findAll();
    }

    @GetMapping("/conta/{id}")
    public ResponseEntity<Account> getAccountByID(@PathVariable(value = "id") Long accountId)
            throws ResourceNotFoundException {
        Account account = repository.findById(accountId).orElseThrow(()->
                new ResourceNotFoundException("Conta nao encontrada: " + accountId));
        return ResponseEntity.ok().body(account);
    }

    @PostMapping("/conta")
    public Account createAccount(@Validated @RequestBody Account account){
        return repository.save(account);
    }

    @PutMapping("/conta/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable(value = "id") Long accountId,
                                           @Validated @RequestBody Account detalhes)
    throws ResourceNotFoundException{
        Account account = repository.findById(accountId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Conta nao encontrada: " + accountId));
        account.setUser(detalhes.getUser());
        account.setSaldo(detalhes.getSaldo());
        account.setNumberAccount(detalhes.getNumberAccount());
        account.setActive(true);
        final Account updatedUser = repository.save(account);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/conta/{id}")
    public Map<String, Boolean> deleteAccount(
            @PathVariable(value = "id") Long accountId
    ) throws Exception{
        Account account = repository.findById(accountId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Conta nao encontrada: " + accountId));
        repository.delete(account);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
