package br.edu.unisep.bank.controller;

import br.edu.unisep.bank.exception.ResourceNotFoundException;
import br.edu.unisep.bank.model.User;
import br.edu.unisep.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/usuarios")
    public List<User> getAllUsers(){
        return repository.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = repository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("Usuario nao encontrado: " + userId));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/usuarios/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable(value = "email") String userEmail) {
        User user = repository.findByEmail(userEmail);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/usuarios")
    public User createUser(@Validated @RequestBody User user){
        user.setIsActive(true);
        return repository.save(user);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
                                           @Validated @RequestBody User detalhes)
    throws ResourceNotFoundException{
        User user = repository.findById(userId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Usuario nao encontrado: " + userId));
        user.setEmail(detalhes.getEmail());
        user.setSobrenome(detalhes.getSobrenome());
        user.setNome(detalhes.getNome());
        user.setSenha(detalhes.getSenha());
        user.setAlteradoEm(new Date());
        user.setCpf(detalhes.getCpf());
        user.setBirth(detalhes.getBirth());
        user.setIsActive(detalhes.getIsActive());
        final User updatedUser = repository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/usuarios/{id}")
    public Map<String, Boolean> deleteUser(
            @PathVariable(value = "id") Long userId
    ) throws Exception{
        User user = repository.findById(userId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Usuario nao encontrado: " + userId));
        repository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
