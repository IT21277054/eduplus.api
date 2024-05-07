package com.ds.assignment.coursemanagementservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "quiz")
public class Quiz {
    @Id
    private String quizId;
    private String courseId;
    private String title; // New field for title
    private String description; // New field for description
    private List<Question> questions;

    // Setter method for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Setter method for description
    public void setDescription(String description) {
        this.description = description;
    }
}
