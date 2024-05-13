package com.ds.assignment.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollCourse {
    private String userId;
    private String courseId;
    private int progress = 0;
    private String status = "enrolled";

}