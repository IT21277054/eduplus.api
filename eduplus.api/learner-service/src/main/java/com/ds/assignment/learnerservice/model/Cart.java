package com.ds.assignment.learnerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.List;

@Document(value = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Cart {
    @MongoId
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;
    private String account_id;
    private List<String> course_id;

}

