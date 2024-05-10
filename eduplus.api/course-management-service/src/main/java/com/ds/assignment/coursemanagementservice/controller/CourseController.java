package com.ds.assignment.coursemanagementservice.controller;

import com.ds.assignment.coursemanagementservice.dto.CourseRequest;
import com.ds.assignment.coursemanagementservice.dto.CourseResponse;
import com.ds.assignment.coursemanagementservice.model.Course;
import com.ds.assignment.coursemanagementservice.repository.CourseRepository;
import com.ds.assignment.coursemanagementservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
@Validated
public class CourseController {
    private final CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourse(@RequestParam("course_id") String courseId,
                             @RequestParam("instructor_id") String instructorId,
                             @RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("price") BigDecimal price,
                             @RequestParam("image") MultipartFile image) throws IOException {
        CourseRequest courseRequest = CourseRequest.builder()
                .course_id(courseId)
                .instructor_id(instructorId)
                .title(title)
                .description(description)
                .price(price)
                .image(image)
                .build();
        courseService.createCourse(courseRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CourseResponse> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable String courseId) {
        CourseResponse courseResponse = courseService.getCourseById(courseId);
        return ResponseEntity.ok(courseResponse);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Void> updateCourse(@PathVariable String courseId,
                                             @ModelAttribute CourseRequest courseRequest) throws IOException {
        courseService.updateCourse(courseId, courseRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable String courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted successfully");
    }

    @GetMapping("/status/pending")
    public List<Course> getPendingCourses(@RequestHeader(value = "loggedInUserRole") String userRole) {
        System.out.println(userRole);
        if (!Objects.equals(userRole, "ADMIN")) {
            throw new RuntimeException("Access denied");
        }
        return courseRepository.findByStatus("pending");
    }

    @PutMapping("/{id}/status")
    public Course updateCourseStatus(@RequestHeader("loggedInUserRole") String userRole, @PathVariable String id, @RequestParam String status ) {
        if (!Objects.equals(userRole, "ADMIN")) {
            throw new RuntimeException("Access denied");
        }
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        course.setStatus(status);
        return courseRepository.save(course);
    }
}
