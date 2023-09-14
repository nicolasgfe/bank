package br.edu.unisep.bank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

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
    private Float saldo;
    @OneToOne
    @JoinColumn(name = "user")
    private User user;
    @Column(name = "isActive")
    private boolean isActive;
}
