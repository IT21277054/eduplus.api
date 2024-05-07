// Unit.java
package com.ds.assignment.coursemanagementservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "unit")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Unit {
    @Id
    private String unitId;
    private String courseId;
    private List<UnitDetails> units;

    // Setter and Getter methods
}
