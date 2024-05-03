package com.ds.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrolledCourse {
    @MongoId
    @Field(targetType = FieldType.OBJECT_ID)
    private String courseId;
    private int progress = 0;
    private String status = "enrolled";
}
