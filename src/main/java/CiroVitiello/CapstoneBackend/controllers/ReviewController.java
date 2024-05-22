package CiroVitiello.CapstoneBackend.controllers;

import CiroVitiello.CapstoneBackend.dto.NewReviewsDTO;
import CiroVitiello.CapstoneBackend.entities.Review;
import CiroVitiello.CapstoneBackend.entities.User;
import CiroVitiello.CapstoneBackend.exceptions.BadRequestException;
import CiroVitiello.CapstoneBackend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService rs;


    @GetMapping("/me")
    public List<Review> findByUserID(@AuthenticationPrincipal User currentUser) {
        return this.rs.findByUserId(currentUser.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review save(@RequestBody @Validated NewReviewsDTO body, BindingResult validation, @AuthenticationPrincipal User currentUser) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.rs.save(body, currentUser.getId());
    }

    @PutMapping("/{reviewId}")
    public Review findByIdAndUpdate(@PathVariable UUID reviewId,
                                    @RequestBody @Validated NewReviewsDTO body,
                                    BindingResult validation) {
        if (validation.hasErrors()) throw new BadRequestException(validation.getAllErrors());
        return this.rs.findByIdAndUpdate(reviewId, body);
    }

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID reviewId) {
        this.rs.findByIdAndDelete(reviewId);
    }
}
