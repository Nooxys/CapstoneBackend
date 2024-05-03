package CiroVitiello.CapstoneBackend.repositories;

import CiroVitiello.CapstoneBackend.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionDAO extends JpaRepository<Subscription, UUID> {
}
