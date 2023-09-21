package br.edu.unisep.bank.useCases;

import br.edu.unisep.bank.model.Account;

import java.util.Optional;

public class AccountUseCase {
    public String saque(Account acount, Double value) {
        if (acount.getSaldo() >= value){
            Double newValue = acount.getSaldo() - value;
            acount.setSaldo(newValue);
            return "Saque realizado com sucesso";
        }

        return "Saldo insuficiente!";
    }
    public String transferencia(Account remetente, Account destinatario, Double value) {
        remetente.setSaldo(remetente.getSaldo() - value);
        destinatario.setSaldo(destinatario.getSaldo() + value);
//        String response = "transferencia realizada de "  + new String[]{remetente.getUser().getNome()};
        return "transferencia realizada com sucesso.";
    }

    public String deposito(Account account,  Double value) {
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
