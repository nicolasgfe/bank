package br.edu.unisep.testedocker.controller;


import br.edu.unisep.testedocker.exception.ResourceNotFoundException;
import br.edu.unisep.testedocker.model.*;
import br.edu.unisep.testedocker.repository.AccountRepository;
import br.edu.unisep.testedocker.repository.TransactionRepository;
import br.edu.unisep.testedocker.repository.UserRepository;
import br.edu.unisep.testedocker.useCases.AccountUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    @Autowired
    private TransactionRepository repository;

    @Autowired AccountRepository accountRepository;
    @Autowired UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    private AccountUseCase accountUseCase;

    public TransactionController() {
        accountUseCase = new AccountUseCase();
    }

    @GetMapping("/transacao")
    public List<Transaction> getAllTransactions(){
        return repository.findAll();
    }

    @GetMapping("/teste")
    public String teste(){
        return "OKKKKKK";
    }

    @GetMapping("/transacao/{id}")
    public ResponseEntity<Transaction> getTransactionByID(@PathVariable(value = "id") Long transactionId)
            throws ResourceNotFoundException {
        Transaction transaction = repository.findById(transactionId).orElseThrow(()->
                new ResourceNotFoundException("Transacao nao encontrado: " + transactionId));
        return ResponseEntity.ok().body(transaction);
    }

    @PostMapping("/transacao")
    public Transaction createTransaction(@Validated @RequestBody Transaction user){
        return repository.save(user);
    }

    @PutMapping("/transacao/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable(value = "id") Long transactionId,
                                                         @Validated @RequestBody Transaction detalhes)
            throws ResourceNotFoundException{
        Transaction transaction = repository.findById(transactionId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Transacao nao encontrado: " + transactionId));
        transaction.setTipoTransacao(detalhes.getTipoTransacao());
        transaction.setRemetente(detalhes.getRemetente());
        transaction.setDestinatario(detalhes.getDestinatario());
        transaction.setValor(detalhes.getValor());
        final Transaction updatedUser = repository.save(transaction);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/transacao/{id}")
    public Map<String, Boolean> deleteTransaction(
            @PathVariable(value = "id") Long transactionId
    ) throws Exception {
        Transaction transaction = repository.findById(transactionId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Transacao nao encontrado: " + transactionId));
        repository.delete(transaction);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PostMapping("/trasacao/transferencia")
    public String transaction(@RequestBody Transferencia body) throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);
        Optional<Account> accountOptional = accountRepository.findById(user.getId());
        Account accountRemetente = accountOptional.get();


        Long accountIdDestinatario = body.getAccountDestinatario();
        Optional<Account> accountDestinatarioOptinoal = accountRepository.findById(accountIdDestinatario);
        Account accountDestinatario = accountDestinatarioOptinoal.get();
        Transaction transaction = accountUseCase.transferencia(accountRemetente, accountDestinatario, body.getValue());
        accountRepository.save(accountRemetente);
        accountRepository.save(accountDestinatario);
        transactionRepository.save(transaction);
        return "Transferencia realizada com sucesso.";
    }

    @PostMapping("/transacao/saque")
    public String saque(@RequestBody Saque body) throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);
        Optional<Account> accountOptional = accountRepository.findById(user.getId());
        Account account = accountOptional.get();

        Transaction transaction = accountUseCase.saque(account, body.getValue());
        accountRepository.save(account);
        transactionRepository.save(transaction);
        return "Transferencia realizada com sucesso.";
    }


    @PostMapping("/transacao/deposito")
    public String deposito(@RequestBody Saque body) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);
        Optional<Account> accountOptional = accountRepository.findById(user.getId());
        Account account = accountOptional.get();

        if (accountOptional.isPresent()) {
            if (body.getValue() > 0) {
                Transaction transaction = accountUseCase.deposito(account, body.getValue());
                transactionRepository.save(transaction);
                accountRepository.save(account);
                return "Deposito realizado com sucesso.";
            } else {
                return "O valor do depósito não pode ser menor ou igual a zero!";
            }
        } else {
            throw new ResourceNotFoundException("Conta não encontrada: ");
        }
    }

    @PostMapping("/transacao/pdf")
    public void deposito() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);
        Optional<Account> accountOptional = accountRepository.findById(user.getId());
        Account account = accountOptional.get();
        accountUseCase.extrato(account);
    }
}
