package com.ds.assignment.coursemanagementservice.service;

import com.ds.assignment.coursemanagementservice.model.Quiz;
import com.ds.assignment.coursemanagementservice.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {
    private final QuizRepository quizRepository;

    public void addQuizToCourse(String courseId, Quiz quiz) {
        if (quiz == null) {
            throw new IllegalArgumentException("Quiz cannot be null");
        }
        quiz.setCourseId(courseId);
        quizRepository.save(quiz);
        log.info("Quiz added to course with ID: {}", courseId);
    }

    public Quiz getQuizById(String quizId) {
        return quizRepository.findById(quizId).orElse(null);
    }

    public void updateQuiz(String quizId, Quiz updatedQuiz) {
        Quiz existingQuiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        // Update the existing quiz with new information
        existingQuiz.setTitle(updatedQuiz.getTitle());
        existingQuiz.setDescription(updatedQuiz.getDescription());
        existingQuiz.setQuestions(updatedQuiz.getQuestions());

        quizRepository.save(existingQuiz);
        log.info("Quiz updated with ID: {}", quizId);
    }

    public void deleteQuiz(String quizId) {
        quizRepository.deleteById(quizId);
        log.info("Quiz deleted with ID: {}", quizId);
    }

    public Quiz getQuizByCourseId(String courseId) {
        return quizRepository.findByCourseId(courseId);
    }
}
