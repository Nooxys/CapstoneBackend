package CiroVitiello.CapstoneBackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewUserDTO(@NotEmpty(message = "Username is required!")
                         @Size(min = 2, max = 10, message = " your name must be  between 3 and 10 characters!")
                         String name,
                         @NotEmpty(message = "Username is required!")
                         @Size(min = 2, max = 15, message = " your surname must be  between 3 and 15 characters!")
                         String surname,
                         @NotEmpty(message = "Username is required!")
                         @Size(min = 3, max = 8, message = " your username must be  between 3 and 8 characters!")
                         String username,
                         @NotEmpty(message = "email is required!")
                         @Email(message = "please check your email format!")
                         String email,
                         @NotEmpty(message = "password is required!")
                         String password,
                         @NotNull(message = "a birth date is required!")
                         LocalDate birthDate) {
}
