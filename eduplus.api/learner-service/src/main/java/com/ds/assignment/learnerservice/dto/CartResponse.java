package com.ds.assignment.learnerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping
public class CartResponse {
    private String account_id;
    private List<String> course_id;

}
