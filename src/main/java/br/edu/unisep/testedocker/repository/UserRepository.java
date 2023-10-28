package br.edu.unisep.testedocker.repository;

import br.edu.unisep.testedocker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from usuario where email = :email",
            nativeQuery = true)
    User findByEmail(@Param("email") String email);

    User findByUsername(String username);
}
