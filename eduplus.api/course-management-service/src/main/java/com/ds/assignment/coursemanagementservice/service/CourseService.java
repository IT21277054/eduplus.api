package com.ds.assignment.coursemanagementservice.service;

import com.ds.assignment.coursemanagementservice.dto.CourseRequest;
import com.ds.assignment.coursemanagementservice.dto.CourseResponse;
import com.ds.assignment.coursemanagementservice.model.Course;
import com.ds.assignment.coursemanagementservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;

    public void createCourse(CourseRequest courseRequest) throws IOException {
        String courseId = courseRequest.getCourse_id();
        Course course = Course.builder()
                .course_id(courseId)
                .instructor_id(courseRequest.getInstructor_id())
                .title(courseRequest.getTitle())
                .description(courseRequest.getDescription())
                .price(courseRequest.getPrice())
                .imageUrl(uploadImage(courseRequest.getImage()))
                .status("pending")
                .build();

        courseRepository.save(course);
        log.info("Course {} is saved", course.getCourse_id());
    }

    public List<CourseResponse> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(this::mapToCourseResponse).collect(Collectors.toList());
    }

    public CourseResponse getCourseById(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + courseId));
        return mapToCourseResponse(course);
    }

    public void updateCourse(String courseId, CourseRequest courseRequest) throws IOException {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Update the existing course with new information from courseRequest
        existingCourse.setTitle(courseRequest.getTitle());
        existingCourse.setDescription(courseRequest.getDescription());
        existingCourse.setPrice(courseRequest.getPrice());
        //existingCourse.setImageUrl(courseRequest.getImage());

        courseRepository.save(existingCourse);
        log.info("Course {} updated successfully", courseId);
    }

    public void deleteCourse(String courseId) {
        courseRepository.deleteById(courseId);
        log.info("Course {} is deleted", courseId);
    }

    private CourseResponse mapToCourseResponse(Course course) {
        return CourseResponse.builder()
                .course_id(course.getCourse_id())
                .instructor_id(course.getInstructor_id())
                .title(course.getTitle())
                .description(course.getDescription())
                .price(course.getPrice())
                .imageUrl(course.getImageUrl())
                .status(course.getStatus()) // Map the status field from the Course entity
                .build();
    }

    private static final String BASE_URL = "http://localhost:8085/api/course";
    private static final String IMAGE_DIRECTORY = "/images";
    private String uploadImage(MultipartFile image) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("Image file is null");
        }

        // Define the upload directory on the server
        String uploadDir = "C:/Users/IMAKA/Desktop/images"; // Change this to your desired upload directory
        String fileName = UUID.randomUUID().toString() + ".jpg"; // Assuming images are in JPG format

        // Save the image to the specified directory
        Path filePath = Paths.get(uploadDir, fileName);
        Files.write(filePath, image.getBytes());

        // Generate the URL for the uploaded image
        return BASE_URL + IMAGE_DIRECTORY + "/" + fileName;
    }
}
