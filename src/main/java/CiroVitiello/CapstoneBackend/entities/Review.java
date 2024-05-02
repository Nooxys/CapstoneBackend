package CiroVitiello.CapstoneBackend.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String title;
    private String description;
    private int rating;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Review(String title, String description, int rating, User user) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.user = user;
    }
}
