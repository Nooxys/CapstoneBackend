package CiroVitiello.CapstoneBackend.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record NewReservationDTO(@NotNull(message = " please insert the ID of the personal trainer")
                                UUID ptId,
                                @NotNull(message = "a date is required!")
                                LocalDate date) {
}
