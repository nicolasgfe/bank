package br.edu.unisep.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "owner")
@Getter
@Setter
public class Owner {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "birth")
    private Date birth;
    @Column(name = "isActive")
    private boolean isActive;

//    @OneToOne(mappedBy = "owner",
//            targetEntity = User.class,
//            fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    @JsonIgnore
//    private User users;
    @OneToOne(mappedBy = "owner",
            targetEntity = Account.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    private Account account;
}
