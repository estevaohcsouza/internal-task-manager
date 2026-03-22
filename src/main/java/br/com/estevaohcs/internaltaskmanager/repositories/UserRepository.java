package br.com.estevaohcs.internaltaskmanager.repositories;

import br.com.estevaohcs.internaltaskmanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);

}
