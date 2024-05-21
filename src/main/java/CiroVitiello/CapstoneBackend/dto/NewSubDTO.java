package CiroVitiello.CapstoneBackend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewSubDTO(@NotEmpty(message = "title is required!")
                        @Size(min = 2, max = 20, message = " a title must be  between 2 and 20 characters!")
                        String title,
                        @NotEmpty(message = "description is required!")
                        @Size(min = 3, max = 500, message = " a description must be  between 3 and 500 characters!")
                        String description,
                        @NotNull(message = "price is required!")
                        double price,
                        @NotNull(message = "how many days this sub last?")
                        int daysOfDuration) {
}
