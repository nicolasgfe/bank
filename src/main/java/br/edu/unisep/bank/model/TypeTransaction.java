package br.edu.unisep.bank.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import br.edu.unisep.bank.model.Transaction;

import java.util.Set;

@Entity
@Table(name = "typeTransaction")
@Getter
@Setter
public class TypeTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "type")
    private String type;
    @OneToMany(mappedBy = "typeTransactionId",
            targetEntity = Transaction.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Transaction> transactions;
}
