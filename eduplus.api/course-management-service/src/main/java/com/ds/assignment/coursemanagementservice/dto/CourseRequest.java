package com.ds.assignment.coursemanagementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping
public class CourseRequest {
    private String course_id;
    private String instructor_id;
    private String title;
    private String description;
    private BigDecimal price;
    private MultipartFile image;
    private String status;
}
