package CiroVitiello.CapstoneBackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RoleChangeDTO(@NotBlank(message = "Role is required")
                            @Pattern(regexp = "USER|PERSONAL_TRAINER|ADMIN", message = "Role must be either USER, PERSONAL_TRAINER or ADMIN")
                            String role) {
}
