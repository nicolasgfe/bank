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
    public String transftransferenciaerencia() {
        return "";
    }

    public String deposito(Account account, Double value) {
        Double newValue = account.getSaldo() + value;
        account.setSaldo(newValue);
        return account.getSaldo().toString();
    }

    public String extrato () {
        return "extrato";
    }

    public String extratoDetalhado() {
        return "extrato detalhado";
    }
}
