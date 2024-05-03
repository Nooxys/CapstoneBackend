package CiroVitiello.CapstoneBackend.services;

import CiroVitiello.CapstoneBackend.dto.NewReviewsDTO;
import CiroVitiello.CapstoneBackend.entities.Review;
import CiroVitiello.CapstoneBackend.exceptions.NotFoundException;
import CiroVitiello.CapstoneBackend.repositories.ReviewDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    private ReviewDAO rd;

    @Autowired
    private UserService es;

    public Page<Review> getReviews(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.rd.findAll(pageable);
    }

    public Review save(NewReviewsDTO body, UUID userId) {
        Review newReview = new Review(body.title(), body.description(), body.rating(), es.findById(userId));
        return this.rd.save(newReview);
    }

    public Review findById(UUID id) {
        return this.rd.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public List<Review> findByUserId(UUID userId) {
        return this.rd.findByUserId(userId);
    }

    public void findByIdAndDelete(UUID id) {
        Review found = this.findById(id);
        this.rd.delete(found);
    }

    public Review findByIdAndUpdate(UUID id, NewReviewsDTO body) {
        Review found = this.findById(id);
        found.setTitle(body.title());
        found.setDescription(body.description());
        found.setRating(body.rating());
        this.rd.save(found);
        return found;
    }

}
