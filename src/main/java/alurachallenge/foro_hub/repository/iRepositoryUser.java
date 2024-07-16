package alurachallenge.foro_hub.repository;

import alurachallenge.foro_hub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface iRepositoryUser extends JpaRepository<User,Integer> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Integer id);
    Optional<User> findByEmail(String email);
}
