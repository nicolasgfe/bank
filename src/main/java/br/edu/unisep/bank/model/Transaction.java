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
    @ManyToOne
    @JoinColumn(name = "remetente_id", nullable = false)
    private Account remetente;
    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Account destinatario;
    @Column(name = "valor", nullable = false)
    private Double valor;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipoTransacao;

    public Transaction() {

    }
}
