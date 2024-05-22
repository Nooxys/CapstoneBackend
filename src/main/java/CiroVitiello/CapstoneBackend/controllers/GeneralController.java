package CiroVitiello.CapstoneBackend.controllers;

import CiroVitiello.CapstoneBackend.entities.Review;
import CiroVitiello.CapstoneBackend.entities.Subscription;
import CiroVitiello.CapstoneBackend.entities.User;
import CiroVitiello.CapstoneBackend.services.ReviewService;
import CiroVitiello.CapstoneBackend.services.SubscriptionService;
import CiroVitiello.CapstoneBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/general")
public class GeneralController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService us;

    @Autowired
    SubscriptionService ss;


    @GetMapping("/trainers")
    public List<User> findTrainers() {
        return this.us.findTrainers();
    }

    @GetMapping("/subscriptions/{subId}")
    public Subscription findSubById(@PathVariable UUID subId) {
        return ss.findById(subId);
    }

    @GetMapping("/subscriptions")
    public Page<Subscription> getAllSubs(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String sortBy) {
        return this.ss.getSubs(page, size, sortBy);
    }

    @GetMapping("/reviews")
    public Page<Review> getAllReviews(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy) {
        return this.reviewService.getReviews(page, size, sortBy);
    }
}
