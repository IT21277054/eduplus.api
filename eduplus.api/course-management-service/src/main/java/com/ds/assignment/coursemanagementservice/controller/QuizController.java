package com.ds.assignment.coursemanagementservice.controller;

import com.ds.assignment.coursemanagementservice.model.Quiz;
import com.ds.assignment.coursemanagementservice.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/course/{courseId}")
    public ResponseEntity<String> addQuizToCourse(@PathVariable("courseId") String courseId,
                                                  @RequestBody Quiz quiz) {
        try {
            quizService.addQuizToCourse(courseId, quiz);
            return ResponseEntity.ok("Quiz added to course successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable("quizId") String quizId) {
        Quiz quiz = quizService.getQuizById(quizId);
        if (quiz != null) {
            return ResponseEntity.ok(quiz);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{quizId}")
    public ResponseEntity<String> updateQuiz(@PathVariable("quizId") String quizId,
                                             @RequestBody Quiz updatedQuiz) {
        try {
            quizService.updateQuiz(quizId, updatedQuiz);
            return ResponseEntity.ok("Quiz updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<String> deleteQuiz(@PathVariable("quizId") String quizId) {
        try {
            quizService.deleteQuiz(quizId);
            return ResponseEntity.ok("Quiz deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{courseId}/quiz")
    public List<Quiz> getQuizByCourseId(@PathVariable String courseId) {
        return quizService.getQuizByCourseId(courseId);
    }
}
