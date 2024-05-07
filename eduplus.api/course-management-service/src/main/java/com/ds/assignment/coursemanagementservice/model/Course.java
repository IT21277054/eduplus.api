package com.ds.assignment.coursemanagementservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.math.BigDecimal;
@Document(value = "course")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Course {
    @MongoId
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;
    private String course_id;
    @Field(targetType = FieldType.OBJECT_ID)
    private String instructor_id;
    private String title;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private String status;

}
