package com.ds.assignment.coursemanagementservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "lectnote")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class LectNote {
    @Id
    private String note_id;
    private String content_id;
    private String description;
    private byte[] fileData;
}
