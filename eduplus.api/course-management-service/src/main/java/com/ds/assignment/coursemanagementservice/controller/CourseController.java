package com.ds.assignment.coursemanagementservice.controller;

import com.ds.assignment.coursemanagementservice.dto.CourseRequest;
import com.ds.assignment.coursemanagementservice.dto.CourseResponse;
import com.ds.assignment.coursemanagementservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

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
}
