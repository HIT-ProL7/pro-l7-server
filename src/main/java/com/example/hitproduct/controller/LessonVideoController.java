package com.example.hitproduct.controller;
/*
 * @author HongAnh
 * @created 14 / 07 / 2024 - 12:35 AM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.Endpoint;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.CreateVideoRequest;
import com.example.hitproduct.domain.dto.response.VideoResponse;
import com.example.hitproduct.service.LessonVideoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class LessonVideoController {
    LessonVideoService videoService;

    @PostMapping(Endpoint.V1.LessonVideo.PREFIX)
    public ResponseEntity<GlobalResponse<Meta, VideoResponse>> createVideo(@RequestBody CreateVideoRequest request,
                                                                           @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(videoService.createLessonVideo(request, userDetails.getUsername()));
    }
}
