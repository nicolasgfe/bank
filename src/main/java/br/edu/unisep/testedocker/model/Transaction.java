package br.edu.unisep.testedocker.model;


import br.edu.unisep.testedocker.Enum.TipoTransacao;
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
