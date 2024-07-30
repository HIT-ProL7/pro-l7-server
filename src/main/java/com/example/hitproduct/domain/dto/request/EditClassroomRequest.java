package com.example.hitproduct.domain.dto.request;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
public class EditClassroomRequest {
    private String name;
    private MultipartFile logoImg;
    private String description;
    private MultipartFile roadmapImg;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private OffsetDateTime startedDate;
}
