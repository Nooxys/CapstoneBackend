package CiroVitiello.CapstoneBackend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewReviewsDTO(@NotEmpty(message = "title is required!")
                            @Size(max = 20, message = "the title must be max 20 characters")
                            String title,
                            @NotEmpty(message = "description is required!")
                            String description,
                            @NotNull(message = "a rating is required!")
                            int rating) {
}
