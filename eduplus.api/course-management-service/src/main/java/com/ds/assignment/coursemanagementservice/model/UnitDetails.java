// UnitDetails.java
package com.ds.assignment.coursemanagementservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitDetails {
    private int unitNumber;
    private String title;
    private byte[] video;
    private String videoUrl;
    private byte[] lectureNotes;

    // Setter and Getter methods
}
