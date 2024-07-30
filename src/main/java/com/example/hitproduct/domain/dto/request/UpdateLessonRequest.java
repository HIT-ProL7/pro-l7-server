package com.example.hitproduct.domain.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record UpdateLessonRequest(
        String title,
        String name,
        String content
) {
}
