package CiroVitiello.CapstoneBackend.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "subscriptions")
@Data
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String cover;
    private String title;
    private String description;
    private double price;
    @Column(name = "days_duration")
    private int DaysOfDuration;
    @ManyToMany
    @JoinTable(name = "subscriptions_users",
            joinColumns = @JoinColumn(name = "sub_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    public Subscription(String title, String description, double price, int daysOfDuration) {
        setTemporaryCover();
        this.title = title;
        this.description = description;
        this.price = price;
        this.DaysOfDuration = daysOfDuration;
    }

    public void setTemporaryCover() {
        this.cover = "https://placehold.co/600x400";
    }
}
