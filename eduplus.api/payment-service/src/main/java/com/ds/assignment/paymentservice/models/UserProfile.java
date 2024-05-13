package com.ds.assignment.paymentservice.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "learnerprofile")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserProfile {
    @MongoId
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;
    @Field(targetType = FieldType.OBJECT_ID)
    private String account_id;
    @NotNull
    @NotEmpty
    private String name;
    @Pattern(regexp="^\\+(?:[0-9] ?){6,14}[0-9]$", message="Invalid phone number")
    private String phoneNumber;
    @NotNull
    @NotEmpty
    @Email(message = "Invalid email address")
    @Pattern(regexp = ".+@.+\\..+",message ="Invalid email address" )
    private String email;
    private List<EnrolledCourse> courseList;
}
