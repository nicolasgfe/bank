package br.edu.unisep.bank.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(name = "remetente")
    private String remetente;
    @Column(name = "destinatario")
    private String destinatario;
    @NotNull
    @Column(name = "valor")
    private float valor;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "typeTransactionId")
    private TypeTransaction typeTransactionId;
}
