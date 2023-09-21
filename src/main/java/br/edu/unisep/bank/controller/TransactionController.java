package br.edu.unisep.bank.controller;

import br.edu.unisep.bank.exception.ResourceNotFoundException;
import br.edu.unisep.bank.model.Account;
import br.edu.unisep.bank.model.Saque;
import br.edu.unisep.bank.model.Transaction;
import br.edu.unisep.bank.model.Transferencia;
import br.edu.unisep.bank.repository.AccountRepository;
import br.edu.unisep.bank.repository.TransactionRepository;
import br.edu.unisep.bank.useCases.AccountUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    @Autowired
    private TransactionRepository repository;

    @Autowired AccountRepository accountRepository;

    private AccountUseCase accountUseCase;

    public TransactionController() {
        accountUseCase = new AccountUseCase();
    }

    @GetMapping("/transacao")
    public List<Transaction> getAllTransactions(){
        return repository.findAll();
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
        transaction.setTypeTransactionId(detalhes.getTypeTransactionId());
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
        //será  implementado para o mesmo pegar o id da conta pelo relacionamento com a conta. Ao inves de passar por parametros
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails username = (UserDetails) auth.getPrincipal();
        String teste = username.getUsername();
        Long accountIdRemetente = body.getAccountRemetente();
        Long accountIdDestinatario = body.getAccountDestinatario();
        Optional<Account> accountRemetenteOptional = accountRepository.findById(accountIdRemetente);
        Optional<Account> accountDestinatarioOptinoal = accountRepository.findById(accountIdDestinatario);
//        if (body.getValue() <= 0) {
//            return "O valor do saque não pode ser menor que zero!";
//        }
        Account accountRemetente = accountRemetenteOptional.get();
        Account accountDestinatario = accountDestinatarioOptinoal.get();
        String data = accountUseCase.transferencia(accountRemetente, accountDestinatario, body.getValue());
        accountRepository.save(accountRemetente);
        accountRepository.save(accountDestinatario);
        return "data";
    }

    @PostMapping("/transacao/saque/{id}")
    public String saque(@PathVariable(value = "id") Long accountId, @RequestBody Saque body) throws Exception{
        //será  implementado para o mesmo pegar o id da conta pelo relacionamento com a conta. Ao inves de passar por parametros
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails username = (UserDetails) auth.getPrincipal();
        String teste = username.getUsername();
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        Account account = accountOptional.get();
//        if (body.getValue() >= 0) {
//            return "O valor do saque não pode ser menor que zero!";
//        }

//        if (account.getUser().getUsername().equals(teste)) {
        String data = accountUseCase.saque(account, body.getValue());
//        }
        accountRepository.save(account);
        return data;
//        return "Você não tem permissão para ver saldo de outra conta!";
    }


    @PostMapping("/transacao/deposito/{id}")
    public String deposito(@PathVariable(value = "id") Long accountId, @RequestBody Saque body) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();

        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            if (body.getValue() > 0) {
                String data = accountUseCase.deposito(account, body.getValue());
                accountRepository.save(account);
                return data;
            } else {
                return "O valor do depósito não pode ser menor ou igual a zero!";
            }
        } else {
            throw new ResourceNotFoundException("Conta não encontrada: " + accountId);
        }
    }
}
