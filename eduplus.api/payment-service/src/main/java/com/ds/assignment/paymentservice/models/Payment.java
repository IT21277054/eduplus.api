package com.ds.assignment.paymentservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @MongoId
    @Field(targetType = FieldType.OBJECT_ID)
    private String userId;
    private String courseId;
    private int amount = 0;
    private String date;
}
