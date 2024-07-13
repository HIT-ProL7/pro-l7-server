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
import com.example.hitproduct.domain.dto.request.LessonVideoRequest;
import com.example.hitproduct.domain.dto.response.VideoResponse;
import com.example.hitproduct.service.LessonVideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class LessonVideoController {
    LessonVideoService videoService;

    @Operation(summary = "Create a new lesson video")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Video created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(Endpoint.V1.LessonVideo.PREFIX)
    public ResponseEntity<GlobalResponse<Meta, VideoResponse>> createVideo(@RequestBody LessonVideoRequest request,
                                                                           @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(videoService.createLessonVideo(request, userDetails.getUsername()));
    }

    @Operation(summary = "Edit an existing lesson video")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Video edited successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(Endpoint.V1.LessonVideo.LESSON_VIDEO_ID)
    public ResponseEntity<GlobalResponse<Meta, VideoResponse>> editLessonVideo(@PathVariable(name = "videoId") Integer id,
                                                                               @RequestBody LessonVideoRequest request,
                                                                               @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(videoService.editLessonVideo(id, request, userDetails.getUsername()));
    }

    @Operation(summary = "Delete a lesson video")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Video deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping(Endpoint.V1.LessonVideo.DELETE_LESSON_VIDEO)
    public ResponseEntity<GlobalResponse<Meta, String>> deleteLessonVideo(@PathVariable(name = "videoId")Integer videoId,
                                                                          @PathVariable(name = "lessonId") Integer lessonId,
                                                                          @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(videoService.deleteLessonVideo(videoId, lessonId, userDetails.getUsername()));
    }
}
