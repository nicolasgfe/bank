package br.edu.unisep.bank.model;

import jakarta.persistence.*;
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
    @Column(name = "remetente")
    private String remetente;
    @Column(name = "destinatario")
    private String destinatario;
    @Column(name = "valor")
    private float valor;
    @ManyToOne
    @JoinColumn(name = "typeTransactionId")
    private TypeTransaction typeTransactionId;
}
