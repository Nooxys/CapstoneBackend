package CiroVitiello.CapstoneBackend.repositories;

import CiroVitiello.CapstoneBackend.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationDAO extends JpaRepository<Reservation, UUID> {
    Optional<Reservation> findByClientIdAndPtId(UUID clientId, UUID ptId);

    Optional<Reservation> findByIdAndClientId(UUID id, UUID clientId);

    List<Reservation> findByClientId(UUID clientId);
}
