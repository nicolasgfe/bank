package br.edu.unisep.testedocker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "Account")
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "numberAccount")
    private Long numberAccount;
    @Column(name = "saldo")
    private Double saldo;
    @OneToOne
    @JoinColumn(name = "user")
    private User user;
    @Column(name = "isActive")
    private boolean isActive;

    @OneToMany(mappedBy = "remetente",
            targetEntity = Transaction.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Transaction> remetente;
    @OneToMany(mappedBy = "destinatario",
            targetEntity = Transaction.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Transaction> destinatario;
}