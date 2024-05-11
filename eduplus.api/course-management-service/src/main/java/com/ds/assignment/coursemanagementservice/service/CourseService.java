package com.ds.assignment.coursemanagementservice.service;

import com.ds.assignment.coursemanagementservice.dto.CourseRequest;
import com.ds.assignment.coursemanagementservice.dto.CourseResponse;
import com.ds.assignment.coursemanagementservice.model.Course;
import com.ds.assignment.coursemanagementservice.repository.CourseRepository;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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

    @Autowired
    private final Storage storage;


    public void createCourse(CourseRequest courseRequest) throws IOException {
        System.out.println("here at course creation");
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


    public String uploadImage(MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Image file is null or empty");
        }

        String fileName = UUID.randomUUID().toString() + ".png";
        String bucketName = "eduplus-5bc5e.appspot.com";

        try {
            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType("image/png")
                    .build();
            Blob blob = storage.create(blobInfo, image.getBytes());
            String imageUrl = "https://firebasestorage.googleapis.com/v0/b/" + bucketName + "/o/" + fileName + "?alt=media";;
            return imageUrl;
        } catch (Exception e) {
            log.error("Error uploading image to Cloud Storage", e);
            throw new IOException("Error uploading image to Cloud Storage", e);
        }
    }

}
