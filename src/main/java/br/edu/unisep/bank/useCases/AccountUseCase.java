package br.edu.unisep.bank.useCases;

import br.edu.unisep.bank.Enum.TipoTransacao;
import br.edu.unisep.bank.model.Account;
import br.edu.unisep.bank.model.Transaction;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


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

    public Transaction extrato (Account account, List<Transaction> transactionsBy, List<Transaction> transactionsfrom) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("C:\\Nova Pasta/PDF_extrato.pdf"));
            document.open();

            document.add(new Paragraph("Gerando PDF - Java"));
            document.add(new Paragraph("Transações do " + account.getUser().getNome()));
            document.add(new Paragraph(account.getSaldo().toString()));
            document.add(new Paragraph(account.getNumberAccount()));


        }
        catch(DocumentException de) {
                System.err.println(de.getMessage());
            }
      catch(IOException ioe) {
                System.err.println(ioe.getMessage());
            }
        document.close();
        return null;
    }

    public String extratoDetalhado() {
        return "extrato detalhado";
    }
}
