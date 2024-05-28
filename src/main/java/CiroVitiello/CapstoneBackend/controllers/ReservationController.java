package CiroVitiello.CapstoneBackend.controllers;

import CiroVitiello.CapstoneBackend.dto.NewReservationDTO;
import CiroVitiello.CapstoneBackend.entities.Reservation;
import CiroVitiello.CapstoneBackend.entities.User;
import CiroVitiello.CapstoneBackend.exceptions.BadRequestException;
import CiroVitiello.CapstoneBackend.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService rs;

    @GetMapping("/me")
    public List<Reservation> getMyReservations(
            @AuthenticationPrincipal User currentUser) {
        return this.rs.getMyReservations(currentUser.getId());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Reservation> getAllReservations(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.rs.getReservations(page, size, sortBy);
    }

    @GetMapping("/{reservationId}")
    public Reservation findReservationById(@PathVariable UUID reservationId, @AuthenticationPrincipal User currentUser) {
        return rs.findById(reservationId, currentUser.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation save(@RequestBody @Validated NewReservationDTO body, BindingResult validation, @AuthenticationPrincipal User currentUser) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.rs.save(body, currentUser.getId());
    }

    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID reservationId, @AuthenticationPrincipal User currentUser) {
        this.rs.findByIdAndDelete(reservationId, currentUser.getId());
    }
}
