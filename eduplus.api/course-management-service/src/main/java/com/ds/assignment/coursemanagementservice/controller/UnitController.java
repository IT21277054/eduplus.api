// UnitController.java
package com.ds.assignment.coursemanagementservice.controller;

import com.ds.assignment.coursemanagementservice.model.Quiz;
import com.ds.assignment.coursemanagementservice.model.Unit;
import com.ds.assignment.coursemanagementservice.service.UnitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/unit")
public class UnitController {
    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadUnit(@RequestParam("courseId") String courseId,
                                             @RequestParam("unitNumbers") List<Integer> unitNumbers,
                                             @RequestParam("titles") List<String> titles,
                                             @RequestParam("videos") List<MultipartFile> videos,
                                             @RequestParam("lectureNotes") List<MultipartFile> lectureNotes) {
        try {
            unitService.uploadUnit(courseId, unitNumbers, titles, videos, lectureNotes);
            return ResponseEntity.ok("Units uploaded successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading units: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/{unitId}")
    public ResponseEntity<Unit> getUnitById(@PathVariable String unitId) {
        try {
            Unit unit = unitService.getUnitById(unitId);
            return ResponseEntity.ok(unit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{unitId}")
    public ResponseEntity<String> updateUnit(@PathVariable String unitId,
                                             @RequestParam("courseId") String courseId,
                                             @RequestParam("unitNumbers") Integer unitNumbers,
                                             @RequestParam("titles") String titles,
                                             @RequestParam("videos") MultipartFile videos,
                                             @RequestParam("lectureNotes") MultipartFile lectureNotes) {
        try {
            unitService.updateUnit(unitId, courseId , unitNumbers , titles , videos , lectureNotes);
            return ResponseEntity.ok("Unit updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{unitId}")
    public ResponseEntity<String> deleteUnit(@PathVariable String unitId) {
        try {
            unitService.deleteUnit(unitId);
            return ResponseEntity.ok("Unit deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/{courseId}/unit")
    public List<Unit> getUnitsByCourseId(@PathVariable String courseId) {
        return unitService.getUnitsByCourseId(courseId);
    }
}
