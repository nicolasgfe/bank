package br.edu.unisep.bank.repository;

import br.edu.unisep.bank.model.Transaction;
import br.edu.unisep.bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select * from transaction where remetente_id = :accountId",
            nativeQuery = true)
    List<Transaction> findTransactionByUser(@Param("accountId") Long accountId);

    @Query(value = "select * from transaction where destinatario_id = :accountId",
            nativeQuery = true)
    List<Transaction> findTransactionfromUser(@Param("accountId") Long accountId);


}
