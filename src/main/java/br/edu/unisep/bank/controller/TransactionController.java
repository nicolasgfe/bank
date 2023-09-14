package br.edu.unisep.bank.controller;

import br.edu.unisep.bank.exception.ResourceNotFoundException;
import br.edu.unisep.bank.model.Transaction;
import br.edu.unisep.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    @Autowired
    private TransactionRepository repository;

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
    ) throws Exception{
        Transaction transaction = repository.findById(transactionId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Transacao nao encontrado: " + transactionId));
        repository.delete(transaction);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
