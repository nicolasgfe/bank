package br.edu.unisep.bank.useCases;

import br.edu.unisep.bank.Enum.TipoTransacao;
import br.edu.unisep.bank.model.Account;
import br.edu.unisep.bank.model.Transaction;
import br.edu.unisep.bank.model.User;
import br.edu.unisep.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class AccountUseCase {
    public Transaction saque(Account account, Double value) {
        if (account.getSaldo() >= value){
            Double newValue = account.getSaldo() - value;
            account.setSaldo(newValue);
            Transaction transaction = new Transaction();
            transaction.setRemetente(account);
            transaction.setValor(value);
            transaction.setTipoTransacao(TipoTransacao.SAQUE);
            return transaction;
        }

        return null;
    }
    public Transaction transferencia(Account remetente, Account destinatario, Double value) {
        remetente.setSaldo(remetente.getSaldo() - value);
        destinatario.setSaldo(destinatario.getSaldo() + value);
//        String response = "transferencia realizada de "  + new String[]{remetente.getUser().getNome()};
//        return "transferencia realizada com sucesso.";
        Transaction transaction = new Transaction();
        transaction.setRemetente(remetente);
        transaction.setDestinatario(destinatario);
        transaction.setValor(value);
        transaction.setTipoTransacao(TipoTransacao.SAQUE);
        return transaction;
    }

    public Transaction deposito(Account account,  Double value) {
        Double newValue = account.getSaldo() + value;
        account.setSaldo(newValue);

        Transaction transaction = new Transaction();
        transaction.setRemetente(account);
        transaction.setValor(value);
        transaction.setTipoTransacao(TipoTransacao.DEPOSITO);
        return transaction;
    }

    public String extrato () {
        return "extrato";
    }

    public String extratoDetalhado() {
        return "extrato detalhado";
    }
}
