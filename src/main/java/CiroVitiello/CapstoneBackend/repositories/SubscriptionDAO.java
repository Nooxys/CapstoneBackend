package CiroVitiello.CapstoneBackend.repositories;

import CiroVitiello.CapstoneBackend.entities.Subscription;
import CiroVitiello.CapstoneBackend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionDAO extends JpaRepository<Subscription, UUID> {
    Page<Subscription> findAllByUsers(User user, Pageable pageable);
}
