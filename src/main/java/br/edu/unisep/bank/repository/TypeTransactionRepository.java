package br.edu.unisep.bank.repository;

import br.edu.unisep.bank.model.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeTransactionRepository extends JpaRepository<TypeTransaction, Long> {

}
