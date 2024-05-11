package com.ds.assignment.coursemanagementservice.service;

import com.ds.assignment.coursemanagementservice.model.Unit;
import com.ds.assignment.coursemanagementservice.model.UnitDetails;
import com.ds.assignment.coursemanagementservice.repository.UnitRepository;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;

    @Autowired
    private final Storage storage;
    public Unit uploadUnit(String courseId, List<Integer> unitNumbers, List<String> titles, List<MultipartFile> videos, List<MultipartFile> lectureNotes) throws IOException {
        List<UnitDetails> unitDetailsList = new ArrayList<>();

        for (int i = 0; i < unitNumbers.size(); i++) {
            int unitNumber = unitNumbers.get(i);
            String title = titles.get(i);
            MultipartFile video = videos.get(i);
            MultipartFile notes = lectureNotes.get(i); // Get lecture notes

            byte[] videoData = video.getBytes();
            byte[] notesData = notes.getBytes(); // Convert MultipartFile to byte[]
            String videoUrl = uploadFileToStorage(video);

            UnitDetails unitDetails = UnitDetails.builder()
                    .unitNumber(unitNumber)
                    .title(title)
                    .video(videoData)
                    .videoUrl(videoUrl)
                    .lectureNotes(notesData) // Set lecture notes
                    .build();
            unitDetailsList.add(unitDetails);
        }

        Unit unit = Unit.builder()
                .unitId(UUID.randomUUID().toString())
                .courseId(courseId)
                .units(unitDetailsList)
                .build();

        return unitRepository.save(unit);
    }

    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    public Unit getUnitById(String unitId) {
        return unitRepository.findById(unitId)
                .orElseThrow(() -> new NoSuchElementException("Unit not found with id: " + unitId));
    }

    public void updateUnit(String unitId, String courseId, Integer unitNumber, String title, MultipartFile video, MultipartFile lectureNotes) throws IOException {
        Unit unitToUpdate = unitRepository.findById(unitId)
                .orElseThrow(() -> new NoSuchElementException("Unit not found with id: " + unitId));

        List<UnitDetails> unitDetailsList = unitToUpdate.getUnits();

        UnitDetails unitToUpdateDetails = unitDetailsList.stream()
                .filter(unitDetails -> Objects.equals(unitDetails.getUnitNumber(), unitNumber))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Unit detail not found with unit number: " + unitNumber));

        unitToUpdateDetails.setUnitNumber(unitNumber);
        unitToUpdateDetails.setTitle(title);

        if (video != null && !video.isEmpty()) {
            unitToUpdateDetails.setVideo(video.getBytes());
        }

        if (lectureNotes != null && !lectureNotes.isEmpty()) {
            unitToUpdateDetails.setLectureNotes(lectureNotes.getBytes());
        }

        unitRepository.save(unitToUpdate);
    }

    public void deleteUnit(String unitId) {
        unitRepository.deleteById(unitId);
    }

    public Unit getUnitByCourseId(String courseId) {
        return unitRepository.findByCourseId(courseId);
    }

    private String uploadFileToStorage(MultipartFile file) throws IOException {
        // Generate a unique filename
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // Upload file to Firebase Storage
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder("eduplus-5bc5e.appspot.com", fileName).build(),
                file.getInputStream()
        );

        // Get the download URL of the uploaded file
        System.out.println("blobInfo.getMediaLink()");
        String videoUrl = "https://firebasestorage.googleapis.com/v0/b/eduplus-5bc5e.appspot.com/o/" + fileName + "?alt=media";;
        return videoUrl;
    }
}
