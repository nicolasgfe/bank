package br.edu.unisep.bank.model;

import lombok.Getter;

public class Transferencia {
    @Getter
    private Double value;
    @Getter
    private Long accountRemetente;
    @Getter
    private Long accountDestinatario;
}
