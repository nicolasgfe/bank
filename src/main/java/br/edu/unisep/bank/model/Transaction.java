package br.edu.unisep.bank.model;

import br.edu.unisep.bank.Enum.TipoTransacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "remetente", nullable = false)
    private String remetente;
    @Column(name = "destinatario")
    private String destinatario;
    @Column(name = "valor", nullable = false)
    private Double valor;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipoTransacao;

    public Transaction() {

    }
}
