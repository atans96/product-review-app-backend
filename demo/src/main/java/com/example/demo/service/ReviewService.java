package com.example.demo.service;

import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        updateFeaturedReviews(reviews);
        return reviews;
    }

    private void updateFeaturedReviews(List<Review> reviews) {
        // Reset all featured flags
        reviews.forEach(review -> review.setFeatured(false));

        // Find the highest rating
        int highestRating = reviews.stream()
                .mapToInt(Review::getRating)
                .max()
                .orElse(0);

        // Set featured flag for all reviews with the highest rating
        reviews.stream()
                .filter(review -> review.getRating() == highestRating)
                .forEach(review -> review.setFeatured(true));

        // Save the updated reviews
        reviewRepository.saveAll(reviews);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
    }

    public Review createReview(Review review) {
        review.setDate(new Date());
        review.setReviewSnippet(review.getReviewText().substring(0, Math.min(100, review.getReviewText().length())));
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, Review reviewDetails) {
        Review review = getReviewById(id);
        review.setProductName(reviewDetails.getProductName());
        review.setRating(reviewDetails.getRating());
        review.setReviewText(reviewDetails.getReviewText());
        review.setReviewSnippet(reviewDetails.getReviewText().substring(0, Math.min(100, reviewDetails.getReviewText().length())));
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        Review review = getReviewById(id);
        reviewRepository.delete(review);
    }
    
}