package CiroVitiello.CapstoneBackend.repositories;

import CiroVitiello.CapstoneBackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserDAO extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
