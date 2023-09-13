package br.edu.unisep.bank.controller;

import br.edu.unisep.bank.exception.ResourceNotFoundException;
import br.edu.unisep.bank.model.TypeTransaction;
import br.edu.unisep.bank.repository.TypeTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TypeTransactionController {

    @Autowired
    private TypeTransactionRepository repository;

    @GetMapping("/tipoTransacao")
    public List<TypeTransaction> getAllTypeTransaction(){
        return repository.findAll();
    }

    @GetMapping("/tipoTransacao/{id}")
    public ResponseEntity<TypeTransaction> getTypeTransactionByID(@PathVariable(value = "id") Long typeTranId)
            throws ResourceNotFoundException {
        TypeTransaction typeTransaction = repository.findById(typeTranId).orElseThrow(()->
                new ResourceNotFoundException("Tipo de transação nao encontrado: " + typeTranId));
        return ResponseEntity.ok().body(typeTransaction);
    }

    @PostMapping("/tipoTransacao")
    public TypeTransaction createUser(@Validated @RequestBody TypeTransaction typeTransaction){
        return repository.save(typeTransaction);
    }

    @PutMapping("/tipoTransacao/{id}")
    public ResponseEntity<TypeTransaction> updateTypeTransaction(@PathVariable(value = "id") Long typeTranId,
                                           @Validated @RequestBody TypeTransaction detalhes)
    throws ResourceNotFoundException{
        TypeTransaction typeTransaction = repository.findById(typeTranId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Tipo de transação nao encontrado: " + typeTranId));
        typeTransaction.setType(detalhes.getType());
        final TypeTransaction updatedUser = repository.save(typeTransaction);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/tipoTransacao/{id}")
    public Map<String, Boolean> deleteTypeTransaction(
            @PathVariable(value = "id") Long typeTranId
    ) throws Exception{
        TypeTransaction typeTransaction = repository.findById(typeTranId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Tipo de transação nao encontrado: " + typeTranId));
        repository.delete(typeTransaction);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
