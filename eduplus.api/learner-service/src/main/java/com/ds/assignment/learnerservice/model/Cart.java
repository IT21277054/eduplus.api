package com.ds.assignment.learnerservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(value = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @MongoId
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;
    private String account_id;
    private List<String> course_id;
}
