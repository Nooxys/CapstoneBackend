package CiroVitiello.CapstoneBackend.services;

import CiroVitiello.CapstoneBackend.dto.NewReservationDTO;
import CiroVitiello.CapstoneBackend.entities.Reservation;
import CiroVitiello.CapstoneBackend.exceptions.BadRequestException;
import CiroVitiello.CapstoneBackend.exceptions.NotFoundException;
import CiroVitiello.CapstoneBackend.repositories.ReservationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReservationService {
    @Autowired
    private ReservationDAO rd;

    @Autowired
    private UserService us;

    public Page<Reservation> getReservations(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.rd.findAll(pageable);
    }

    public Page<Reservation> getMyReservations(int page, int size, String sortBy, UUID clientId) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.rd.findAll(pageable);
    }

    public Reservation save(NewReservationDTO body, UUID clientID) {

        this.rd.findByClientIdAndPtId(clientID, body.ptId())
                .ifPresent(reservation -> {
                            throw new BadRequestException("You already have a reservation with this pt!");
                        }
                );
        if (this.rd.existsByClientIdAndDate(clientID, body.date()))
            throw new BadRequestException("You already have a reservation for this date!");
        if (this.rd.existsByPtIdAndDate(body.ptId(), body.date()))
            throw new BadRequestException("This PT already has a reservation for this date!");


        Reservation newRes = new Reservation(us.findById(clientID), us.findById(body.ptId()), body.date());
        return this.rd.save(newRes);
    }

    public Reservation findById(UUID id, UUID clientId) {
        return this.rd.findByIdAndClientId(id, clientId).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(UUID id, UUID clientId) {
        Reservation found = this.findById(id, clientId);
        this.rd.delete(found);
    }

}
