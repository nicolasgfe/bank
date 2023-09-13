package br.edu.unisep.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "primeiro_nome", nullable = false, length = 75)
    private String nome;
    @Column(name = "sobrenome", nullable = false, length = 75)
    private String sobrenome;
    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "senha", nullable = false, length = 100)
    private String senha;

    @Column(name = "criado_em", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date criadoEm;
    @Column(name = "criado_por", nullable = false)
    @CreatedBy
    private String criadoPor;
    @Column(name = "alterado_em")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date alteradoEm;
    @Column(name = "alterado_por")
    @LastModifiedBy
    private String alteradoPor;
}
