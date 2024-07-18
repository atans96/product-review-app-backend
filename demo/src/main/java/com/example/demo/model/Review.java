package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    private String productName;

    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private int rating;

    @NotBlank(message = "Review text is required")
    @Size(min = 10, message = "Review must be at least 10 characters")
    private String reviewText;

    @Column(length = 100)
    private String reviewSnippet;

    private boolean featured;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}