package br.edu.unisep.bank.useCases;

import br.edu.unisep.bank.model.Account;

import java.util.Optional;

public class AccountUseCase {
    public String saque(Optional<Account> acount, Double value) {
        if (acount.get().getSaldo() >= value){
            Double newValue = acount.get().getSaldo() - value;
            acount.get().setSaldo(newValue);
            return "Saque realizado com sucesso";
        }

        return "Saldo insuficiente!" ;
    }
    public String transferencia() {
        return "transferencia";
    }

    public String deposito() {
        return "deposito";
    }

    public String extrato () {
        return "extrato";
    }

    public String extratoDetalhado() {
        return "extrato detalhado";
    }
}
