package br.edu.unisep.testedocker.service;

import br.edu.unisep.testedocker.model.User;
import br.edu.unisep.testedocker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
//        User user = repository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Usuario nao encontrado!");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getSenha(),new ArrayList<>());
    }

    public User save(User user){
        User novo = new User();
        novo.setNome(user.getNome());
        novo.setSobrenome(user.getSobrenome());
        novo.setEmail(user.getEmail());
        novo.setUsername(user.getUsername());
        novo.setSenha(bcryptEncoder.encode(user.getSenha()));
        novo.setCriadoEm(new Date());
        novo.setCriadoPor(user.getNome());
        novo.setAlteradoEm(new Date());
        novo.setAlteradoPor(user.getNome());




        return repository.save(novo);
    }
}
